package com.socialmarketing.commons.objects;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class ObjectUtils {
	private static Log LOG = LogFactory.getLog(ObjectUtils.class);

	  public static boolean setParam(Object paramObject1, String paramString, Object paramObject2)
	  {
	    return setParam(paramObject1, paramString, paramObject2, ".");
	  }

	  public static boolean setParam(Object paramObject1, String paramString1, Object paramObject2, String paramString2)
	  {
	    try
	    {
	      int i = paramString1.indexOf(paramString2);
	      Object localObject;
	      if (i > 0)
	      {
	        localObject = getObject(paramObject1, paramString1.substring(0, i));
	        return setParam(localObject, paramString1.substring(i + 1), paramObject2);
	      }
	      paramString1 = paramString1.substring(0, 1).toUpperCase() + paramString1.substring(1);
	      Method localMethod;
	      if (paramObject2 == null)
	      {
	        localObject = new Class[] { "".getClass() };
	        localMethod = paramObject1.getClass().getMethod("set" + paramString1, (Class[])localObject);
	        localMethod.invoke(paramObject1, new Object[] { paramObject2 });
	      }
	      else
	      {
	        localObject = new Class[] { paramObject2.getClass() };
	        localMethod = paramObject1.getClass().getMethod("set" + paramString1, (Class[])localObject);
	        localMethod.invoke(paramObject1, new Object[] { paramObject2 });
	      }
	    }
	    catch (Exception localException)
	    {
	      return false;
	    }
	    return true;
	  }

	  public static boolean setParam(Object paramObject, String paramString, boolean paramBoolean)
	  {
	    try
	    {
	      paramString = paramString.substring(0, 1).toUpperCase() + paramString.substring(1);
	      Class[] arrayOfClass = { Boolean.TYPE };
	      Method localMethod = paramObject.getClass().getMethod("set" + paramString, arrayOfClass);
	      localMethod.invoke(paramObject, new Object[] { Boolean.valueOf(paramBoolean) });
	    }
	    catch (Exception localException)
	    {
	      return false;
	    }
	    return true;
	  }

	  public static boolean setParam(Object paramObject, String paramString, int paramInt)
	  {
	    try
	    {
	      paramString = paramString.substring(0, 1).toUpperCase() + paramString.substring(1);
	      Class[] arrayOfClass = { Integer.TYPE };
	      Method localMethod = paramObject.getClass().getMethod("set" + paramString, arrayOfClass);
	      localMethod.invoke(paramObject, new Object[] { Integer.valueOf(paramInt) });
	    }
	    catch (Exception localException)
	    {
	      return false;
	    }
	    return true;
	  }

	  public static boolean setParam(Object paramObject, String paramString, double paramDouble)
	  {
	    try
	    {
	      paramString = paramString.substring(0, 1).toUpperCase() + paramString.substring(1);
	      Class[] arrayOfClass = { Double.TYPE };
	      Method localMethod = paramObject.getClass().getMethod("set" + paramString, arrayOfClass);
	      localMethod.invoke(paramObject, new Object[] { Double.valueOf(paramDouble) });
	    }
	    catch (Exception localException)
	    {
	      return false;
	    }
	    return true;
	  }

	  public static String getParam(Object paramObject, String paramString)
	  {
	    try
	    {
	      int i = paramString.indexOf(".");
	      if (i < 1)
	        i = paramString.indexOf("_");
	      if (i > 0)
	      {
	        Object localObject1 = getObject(paramObject, paramString.substring(0, i));
	        return getParam(localObject1, paramString.substring(i + 1));
	      }
	      if ((!paramString.startsWith("has")) && (!paramString.startsWith("is")))
	        paramString = paramString.substring(0, 1).toUpperCase() + paramString.substring(1);
	      Object localObject1 = null;
	      Object localObject2 = null;
	      if (paramString.indexOf("(") > -1)
	      {
	        String str;
	        Class[] arrayOfClass;
	        if (paramString.indexOf("\"") > -1)
	        {
	          str = paramString.substring(paramString.indexOf("\"") + 1, paramString.lastIndexOf("\""));
	          paramString = paramString.substring(0, paramString.indexOf("("));
	          arrayOfClass = new Class[] { String.class };
	          if ((!paramString.startsWith("has")) && (!paramString.startsWith("is")))
	            localObject1 = paramObject.getClass().getMethod("get" + paramString, arrayOfClass);
	          else
	            localObject1 = paramObject.getClass().getMethod(paramString, arrayOfClass);
	          localObject2 = ((Method)localObject1).invoke(paramObject, new Object[] { str });
	        }
	        else
	        {
	          str = paramString.substring(paramString.indexOf("(") + 1, paramString.indexOf(")"));
	          paramString = paramString.substring(0, paramString.indexOf("("));
	          arrayOfClass = new Class[] { Integer.TYPE };
	          if ((!paramString.startsWith("has")) && (!paramString.startsWith("is")))
	            localObject1 = paramObject.getClass().getMethod("get" + paramString, arrayOfClass);
	          else
	            localObject1 = paramObject.getClass().getMethod(paramString, arrayOfClass);
	          localObject2 = ((Method)localObject1).invoke(paramObject, new Object[] { str });
	        }
	      }
	      else
	      {
	        localObject1 = paramObject.getClass().getMethod("get" + paramString, (Class[])null);
	        localObject2 = ((Method)localObject1).invoke(paramObject, (Object[])null);
	      }
	      if (localObject2 == null)
	        return null;
	      if ((localObject2 instanceof String))
	        return (String)localObject2;
	      if ((localObject2 instanceof Timestamp))
	        return String.valueOf(((Timestamp)localObject2).getTime());
	      return String.valueOf(localObject2);
	    }
	    catch (Exception localException)
	    {
	    }
	    return null;
	  }

	  public static int getParamAsInt(Object paramObject, String paramString)
	  {
	    try
	    {
	      return Integer.parseInt(getParam(paramObject, paramString));
	    }
	    catch (Exception localException)
	    {
	    }
	    return -1;
	  }

	  public static boolean invokeMethod(Object paramObject1, String paramString, Object paramObject2)
	  {
	    try
	    {
	      Object localObject;
	      if (paramObject2 != null)
	      {
	        localObject = new Class[] { paramObject2.getClass() };
	        Method localMethod = paramObject1.getClass().getMethod(paramString, (Class[])localObject);
	        localMethod.invoke(paramObject1, new Object[] { paramObject2 });
	      }
	      else
	      {
	        localObject = paramObject1.getClass().getMethod(paramString, new Class[] { null });
	        ((Method)localObject).invoke(paramObject1, new Object[] { null });
	      }
	    }
	    catch (Exception localException)
	    {
	      if (LOG.isDebugEnabled())
	        LOG.debug("invokeMethod Exception on " + paramObject1.getClass().toString() + " with method " + paramString + "(" + paramObject2.getClass().toString() + ")", localException);
	      return false;
	    }
	    return true;
	  }

	  public static Object getObject(Object paramObject, String paramString)
	  {
	    try
	    {
	      paramString = paramString.substring(0, 1).toUpperCase() + paramString.substring(1);
	      Method localMethod = paramObject.getClass().getMethod("get" + paramString, (Class[])null);
	      return localMethod.invoke(paramObject, (Object[])null);
	    }
	    catch (Exception localException)
	    {
	    }
	    return null;
	  }

	  public static Object constructObject(Class paramClass)
	  {
	    try
	    {
	      Class[] arrayOfClass = null;
	      Constructor localConstructor = paramClass.getConstructor(arrayOfClass);
	      Object[] arrayOfObject = null;
	      return localConstructor.newInstance(arrayOfObject);
	    }
	    catch (Exception localException)
	    {
	    }
	    return null;
	  }

	  public static Object constructObject(Class paramClass, Object paramObject, String paramString)
	  {
	    try
	    {
	      Class[] arrayOfClass = { Class.forName(paramString) };
	      Constructor localConstructor = paramClass.getConstructor(arrayOfClass);
	      Object[] arrayOfObject = { paramObject };
	      return localConstructor.newInstance(arrayOfObject);
	    }
	    catch (Exception localException)
	    {
	      LOG.error("constructObject", localException);
	    }
	    return null;
	  }

	  public static Object constructObject(Class paramClass, Object paramObject, Connection paramConnection)
	  {
	    try
	    {
	      Class[] arrayOfClass = { paramObject.getClass(), Class.forName("java.sql.Connection") };
	      Constructor localConstructor = paramClass.getConstructor(arrayOfClass);
	      Object[] arrayOfObject = { paramObject, paramConnection };
	      return localConstructor.newInstance(arrayOfObject);
	    }
	    catch (Exception localException)
	    {
	    }
	    return null;
	  }

	  public static Object constructObject(Class paramClass, Connection paramConnection, int paramInt)
	  {
	    try
	    {
	      Class[] arrayOfClass = { Class.forName("java.sql.Connection"), Integer.TYPE };
	      Constructor localConstructor = paramClass.getConstructor(arrayOfClass);
	      Object[] arrayOfObject = { paramConnection, Integer.valueOf(paramInt) };
	      return localConstructor.newInstance(arrayOfObject);
	    }
	    catch (Exception localException)
	    {
	    }
	    return null;
	  }

	  public static Object constructObject(Class paramClass, Connection paramConnection, int paramInt, String paramString)
	  {
	    try
	    {
	      Class[] arrayOfClass = { Class.forName("java.sql.Connection"), Integer.TYPE, Class.forName("java.lang.String") };
	      Constructor localConstructor = paramClass.getConstructor(arrayOfClass);
	      Object[] arrayOfObject = { paramConnection, Integer.valueOf(paramInt), paramString };
	      return localConstructor.newInstance(arrayOfObject);
	    }
	    catch (Exception localException)
	    {
	    }
	    return null;
	  }

	  public static Object constructObject(Class paramClass, Connection paramConnection, int paramInt, String paramString1, String paramString2)
	  {
	    try
	    {
	      Class[] arrayOfClass = { Class.forName("java.sql.Connection"), Integer.TYPE, Class.forName("java.lang.String"), Class.forName("java.lang.String") };
	      Constructor localConstructor = paramClass.getConstructor(arrayOfClass);
	      Object[] arrayOfObject = { paramConnection, Integer.valueOf(paramInt), paramString1, paramString2 };
	      return localConstructor.newInstance(arrayOfObject);
	    }
	    catch (Exception localException)
	    {
	      if (LOG.isDebugEnabled())
	        LOG.error("constructObject", localException);
	    }
	    return null;
	  }

	  public static byte[] toByteArray(Object paramObject)
	    throws Exception
	  {
	    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
	    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
	    localObjectOutputStream.writeObject(paramObject);
	    localObjectOutputStream.flush();
	    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
	    localObjectOutputStream.close();
	    return arrayOfByte;
	  }

	  public static Object toObject(byte[] paramArrayOfByte)
	    throws Exception
	  {
	    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
	    ObjectInputStream localObjectInputStream = new ObjectInputStream(localByteArrayInputStream);
	    Object localObject = localObjectInputStream.readObject();
	    localObjectInputStream.close();
	    return localObject;
	  }
}
