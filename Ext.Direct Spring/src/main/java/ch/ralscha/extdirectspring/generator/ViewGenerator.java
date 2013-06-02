package ch.ralscha.extdirectspring.generator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.util.ExtDirectSpringUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author hongtao
 * 
 */
public class ViewGenerator {

	private static final Map<JsCacheKey, SoftReference<String>> jsCache = new ConcurrentHashMap<JsCacheKey, SoftReference<String>>();

	private static final Map<ModelCacheKey, SoftReference<ModelBean>> modelCache = new ConcurrentHashMap<ModelCacheKey, SoftReference<ModelBean>>();
	private static final Map<String, SoftReference<List<GridColumnBean>>> columnCache = new ConcurrentHashMap<String, SoftReference<List<GridColumnBean>>>();

	/**
	 * Instrospects the provided class, creates a model object (JS code) and
	 * returns it. This method does not add any validation configuration.
	 * 
	 * @param clazz
	 *            class that the generator should introspect
	 * @param format
	 *            specifies which code (ExtJS or Touch) the generator should
	 *            create
	 * @param debug
	 *            if true the generator creates the output in pretty format,
	 *            false the output is compressed
	 * @return the generated model object (JS code)
	 */

	public static String generateJavascript(Class<?> clazz,
			OutputFormat format, boolean debug) {
		String str = generateJavascript(createColumn(clazz), format, debug);
		return str;// generateJavascript(null, outputConfig);
	}

	/**
	 * Creates JS code based on the provided {@link ModelBean} in the specified
	 * {@link OutputFormat}. Code can be generated in pretty or compressed
	 * format. The generated code is cached unless debug is true. A second call
	 * to this method with the same model name and format will return the code
	 * from the cache.
	 * 
	 * @param model
	 *            generate code based on this {@link ModelBean}
	 * @param format
	 *            specifies which code (ExtJS or Touch) the generator should
	 *            create
	 * @param debug
	 *            if true the generator creates the output in pretty format,
	 *            false the output is compressed
	 * @return the generated model object (JS code)
	 */
	public static String generateJavascript(List<GridColumnBean> columns,
			OutputFormat format, boolean debug) {
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(format);
		outputConfig.setDebug(debug);
		return genColumnScript(columns, outputConfig);
	}

	public static void writeModel(HttpServletRequest request,
			HttpServletResponse response, Class<?> clazz, OutputFormat format,
			boolean debug) throws IOException {
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(format);
		outputConfig.setDebug(debug);
		writeModel(request,response,createColumn(clazz),outputConfig);

	}

	public static void writeModel(HttpServletRequest request,
			HttpServletResponse response, List<GridColumnBean> columns,
			OutputConfig outputConfig) throws IOException {

		byte[] data = genColumnScript(columns, outputConfig).getBytes(
				ExtDirectSpringUtil.UTF8_CHARSET);
		String ifNoneMatch = request.getHeader("If-None-Match");
		String etag = "\"0" + DigestUtils.md5DigestAsHex(data) + "\"";

		if (etag.equals(ifNoneMatch)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}

		response.setContentType("application/javascript");
		response.setCharacterEncoding(ExtDirectSpringUtil.UTF8_CHARSET.name());
		response.setContentLength(data.length);

		response.setHeader("ETag", etag);

		@SuppressWarnings("resource")
		ServletOutputStream out = response.getOutputStream();
		out.write(data);
		out.flush();

	}

	public static List<GridColumnBean> createColumn(Class<?> clazz) {
		Assert.notNull(clazz, "clazz must not be null");

		SoftReference<List<GridColumnBean>> modelReference = columnCache
				.get(clazz.getName());
		if (modelReference != null && modelReference.get() != null) {
			return modelReference.get();
		}

		final Set<String> hasReadMethod = new HashSet<String>();

		BeanInfo bi;
		try {
			bi = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}

		for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
			if (pd.getReadMethod() != null
					&& pd.getReadMethod().getAnnotation(JsonIgnore.class) == null) {
				hasReadMethod.add(pd.getName());
			}
		}
		final List<GridColumnBean> columns = new ArrayList<GridColumnBean>();
		final List<FormFieldBean> fields = new ArrayList<FormFieldBean>();

		ReflectionUtils.doWithFields(clazz, new FieldCallback() {
			private final Set<String> fields = new HashSet<String>();

			@Override
			public void doWith(Field field) throws IllegalArgumentException,
					IllegalAccessException {
				if (!fields.contains(field.getName())
						&& (field.getAnnotation(GridColumn.class) != null
								|| field.getAnnotation(FormField.class) != null || ((Modifier
								.isPublic(field.getModifiers()) || hasReadMethod
								.contains(field.getName())) && field
								.getAnnotation(JsonIgnore.class) == null))) {

					// ignore superclass declarations of fields already found in
					// a subclass
					fields.add(field.getName());

					Class<?> javaType = field.getType();
					// java type 向 Extjs type 转换
					GridColumnType columnType = null;
					for (GridColumnType mt : GridColumnType.values()) {
						if (mt.supports(javaType)) {
							columnType = mt;
							break;
						}
					}

					GridColumnBean columnBean = null;

					GridColumn columnAnnotation = field
							.getAnnotation(GridColumn.class);
					if (columnAnnotation != null) {

						String text;
						if (StringUtils.hasText(columnAnnotation.text())) {
							text = columnAnnotation.text();
						} else {
							text = field.getName();
						}

						GridColumnType xtype;
						if (columnAnnotation.xtype() != GridColumnType.AUTO) {
							xtype = columnAnnotation.xtype();
						} else {
							if (columnType != null) {
								xtype = columnType;
							} else {
								xtype = GridColumnType.AUTO;
							}
						}
						String dataIndex = field.getName();
						if (StringUtils.hasText(columnAnnotation.dataIndex()))
							dataIndex = columnAnnotation.dataIndex();
						columnBean = new GridColumnBean(text, dataIndex, xtype
								.getColumnType());
						if (xtype == GridColumnType.DATECOLUMN) {
							if (StringUtils.hasText(columnAnnotation.format()))
								columnBean.setFormat(columnAnnotation.format());
							else
								columnBean.setFormat("Y-m-d");
						}
						if (xtype == GridColumnType.FLOATCOLUMN) {
							if (StringUtils.hasText(columnAnnotation.format()))
								columnBean.setFormat(columnAnnotation.format());
							else
								columnBean.setFormat("0.00");
						}
						if (xtype == GridColumnType.INTCOLUMN) {
							if (StringUtils.hasText(columnAnnotation.format()))
								columnBean.setFormat(columnAnnotation.format());
							else
								columnBean.setFormat("0000");
						}
						if (columnAnnotation.columnWidth() != 100) {
							columnBean.setColumnWidth(columnAnnotation
									.columnWidth());
						} else {
							columnBean.setColumnWidth(100);
						}
						columns.add(columnBean);
					}
				}
			}
		});
		columnCache.put(clazz.getName(),
				new SoftReference<List<GridColumnBean>>(columns));
		return columns;
	}

	public static String genColumnScript(List<GridColumnBean> columns,
			OutputConfig config) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
		Map<String, Object> viewObject = new LinkedHashMap<String, Object>();
		Map<String, Object> columnObject = new LinkedHashMap<String, Object>();
		//columnObject.put("columns", columns);
		viewObject.put("columns", columns);
		StringBuilder sb = new StringBuilder();
		sb.append("Ext.define(\"").append("SNS.view.grid.Column").append("\",");
		if (config.isDebug()) {
			sb.append("\n");
		}
		String configObjectString;
		try {
			if (config.isDebug()) {
				configObjectString = mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(viewObject);
			} else {
				configObjectString = mapper.writeValueAsString(viewObject);
			}

		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		sb.append(configObjectString);
		sb.append(");");

		String result = sb.toString();
		return result;//"var EXTView = "+configObjectString;
	}

	public static void main(String args[]) throws ClassNotFoundException {
		String str = generateJavascript(createColumn(Class.forName("com.socialmarketing.web.master.model.Company")),
				OutputFormat.EXTJS4, true);

		// str = ModelGenerator.generateJavascript(Company.class,
		// OutputFormat.EXTJS4, true);
		System.out.println(str);
	}

	/**
	 * Clears the model and Javascript code caches
	 */
	public static void clearCaches() {
		modelCache.clear();
		jsCache.clear();
	}

}
