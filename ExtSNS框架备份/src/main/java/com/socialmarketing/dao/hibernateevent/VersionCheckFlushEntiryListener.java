package com.socialmarketing.dao.hibernateevent;

import org.hibernate.HibernateException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.event.internal.DefaultFlushEntityEventListener;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.persister.entity.EntityPersister;

public class VersionCheckFlushEntiryListener extends
		DefaultFlushEntityEventListener {

	private static final long serialVersionUID = 3328666666653406516L;

	@Override
	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		EntityEntry entry = event.getEntityEntry();
		EntityPersister persister = entry.getPersister();

		if (persister.isVersioned()) {
			Object version = persister.getVersion(entity);
			// Make sure version has not changed
			if (!persister.getVersionType()
					.isEqual(version, entry.getVersion())) {
				throw new StaleObjectStateException(persister.getEntityName(),
						entry.getId());
			}
		}
		super.onFlushEntity(event);
	}

}
