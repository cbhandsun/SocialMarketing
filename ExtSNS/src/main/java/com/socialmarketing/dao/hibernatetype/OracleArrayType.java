/**********************************************************************
 * FILE : OracleArrayType.java
 * CREATE DATE : Mar 12, 2010
 * DESCRIPTION :
 *
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 *          
 **********************************************************************
 */
package com.socialmarketing.dao.hibernatetype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.dom4j.Node;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metamodel.relational.Size;
import org.hibernate.type.ForeignKeyDirection;
import org.hibernate.type.Type;

public class OracleArrayType implements Type {

	
	public Object assemble(Serializable cached, SessionImplementor session,
			Object owner) throws HibernateException {
		// TODO 自动生成方法存根
		return null;
	}

	public void beforeAssemble(Serializable cached, SessionImplementor session) {
		// TODO 自动生成方法存根

	}

	public int compare(Object x, Object y, EntityMode entityMode) {
		// TODO 自动生成方法存根
		return 0;
	}

	public Object deepCopy(Object value, EntityMode entityMode,
			SessionFactoryImplementor factory) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value, SessionImplementor session,
			Object owner) throws HibernateException {
		// TODO 自动生成方法存根
		return null;
	}

	public Object fromXMLNode(Node xml, Mapping factory)
			throws HibernateException {
		// TODO 自动生成方法存根
		return null;
	}

	
	public int getColumnSpan(Mapping mapping) throws MappingException {
		// TODO 自动生成方法存根
		return 0;
	}

	
	public int getHashCode(Object x, EntityMode entityMode)
			throws HibernateException {
		// TODO 自动生成方法存根
		return 0;
	}

	
	public int getHashCode(Object x, EntityMode entityMode,
			SessionFactoryImplementor factory) throws HibernateException {
		// TODO 自动生成方法存根
		return 0;
	}

	
	public String getName() {
		// TODO 自动生成方法存根
		return null;
	}

	
	public Class getReturnedClass() {
		return String[].class;
	}

	
	public Type getSemiResolvedType(SessionFactoryImplementor factory) {
		// TODO 自动生成方法存根
		return null;
	}

	
	public Object hydrate(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		// TODO 自动生成方法存根
		return null;
	}

	
	public boolean isAnyType() {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isAssociationType() {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isCollectionType() {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isComponentType() {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isDirty(Object old, Object current,
			SessionImplementor session) throws HibernateException {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isDirty(Object old, Object current, boolean[] checkable,
			SessionImplementor session) throws HibernateException {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isEntityType() {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isEqual(Object x, Object y, EntityMode entityMode)
			throws HibernateException {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isEqual(Object x, Object y, EntityMode entityMode,
			SessionFactoryImplementor factory) throws HibernateException {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isModified(Object oldHydratedState, Object currentState,
			boolean[] checkable, SessionImplementor session)
			throws HibernateException {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isMutable() {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isSame(Object x, Object y, EntityMode entityMode)
			throws HibernateException {
		// TODO 自动生成方法存根
		return false;
	}

	
	public boolean isXMLElement() {
		// TODO 自动生成方法存根
		return false;
	}

	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		
		java.sql.Array sqlArray = rs.getArray(names[0]);
		
		if (!rs.wasNull())
			return sqlArray.getArray();
		else
			return null;
	}

	
	public Object nullSafeGet(ResultSet rs, String name,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {

		java.sql.Array sqlArray = rs.getArray(name);
		
		if (!rs.wasNull())
			return sqlArray.getArray();
		else
			return null;
		
	}

	
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		 if (value == null)
	            st.setNull(index, SQL_TYPES[0]);
	        else {
	            st.setArray(index, (java.sql.Array) value);
	        }
	}

	
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			boolean[] settable, SessionImplementor session)
			throws HibernateException, SQLException {
		// TODO 自动生成方法存根

	}

	
	public Object replace(Object original, Object target,
			SessionImplementor session, Object owner, Map copyCache)
			throws HibernateException {
		// TODO 自动生成方法存根
		return null;
	}

	
	public Object replace(Object original, Object target,
			SessionImplementor session, Object owner, Map copyCache,
			ForeignKeyDirection foreignKeyDirection) throws HibernateException {
		// TODO 自动生成方法存根
		return null;
	}

	
	public Object resolve(Object value, SessionImplementor session, Object owner)
			throws HibernateException {
		// TODO 自动生成方法存根
		return null;
	}

	
	public Object semiResolve(Object value, SessionImplementor session,
			Object owner) throws HibernateException {
		// TODO 自动生成方法存根
		return null;
	}

	
	public void setToXMLNode(Node node, Object value,
			SessionFactoryImplementor factory) throws HibernateException {
		// TODO 自动生成方法存根

	}

	private static final int[] SQL_TYPES = { Types.ARRAY };
	
	
	public int[] sqlTypes(Mapping mapping) throws MappingException {
		 return SQL_TYPES;
	}

	
	public boolean[] toColumnNullness(Object value, Mapping mapping) {
		// TODO 自动生成方法存根
		return null;
	}

	
	public String toLoggableString(Object value,
			SessionFactoryImplementor factory) throws HibernateException {
		// TODO 自动生成方法存根
		return null;
	}

	public Size[] dictatedSizes(Mapping mapping) throws MappingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Size[] defaultSizes(Mapping mapping) throws MappingException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSame(Object x, Object y) throws HibernateException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEqual(Object x, Object y) throws HibernateException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEqual(Object x, Object y, SessionFactoryImplementor factory)
			throws HibernateException {
		// TODO Auto-generated method stub
		return false;
	}

	public int getHashCode(Object x) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getHashCode(Object x, SessionFactoryImplementor factory)
			throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int compare(Object x, Object y) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object deepCopy(Object value, SessionFactoryImplementor factory)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
