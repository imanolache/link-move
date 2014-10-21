package com.nhl.link.etl.load.cayenne;

import java.util.List;
import java.util.Map;

import org.apache.cayenne.DataObject;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.map.DbAttribute;
import org.apache.cayenne.map.DbEntity;
import org.apache.cayenne.map.ObjAttribute;
import org.apache.cayenne.map.ObjEntity;

import com.nhl.link.etl.EtlRuntimeException;

// TODO: this strategy should be merged into superclass once we start programming IDs using "db:" expressions... 
// ID update/merge mechanism is generic and does not depend on the selected mapper.
public class CayenneCreateOrUpdateWithPKStrategy<T extends DataObject> extends DefaultCayenneCreateOrUpdateStrategy<T> {
	private final String primaryKeyAttribute;

	public CayenneCreateOrUpdateWithPKStrategy(List<RelationshipInfo> relationships, String primaryKeyAttribute) {
		super(relationships);
		this.primaryKeyAttribute = primaryKeyAttribute;
	}

	@Override
	public T create(ObjectContext context, Class<T> type, Map<String, Object> source) {
		T target = context.newObject(type);
		update(context, source, target);

		// merge ID..

		// See TODO above ... this is a hack... id merging should happen
		// regardless of the IDMapper strategy

		ObjEntity entity = context.getEntityResolver().getObjEntity(type);
		DbEntity dbEntity = entity.getDbEntity();
		DbAttribute pk = dbEntity.getAttribute(primaryKeyAttribute);

		// sanity check
		if (pk == null) {
			throw new EtlRuntimeException("'" + primaryKeyAttribute + "' is not a column in '" + dbEntity.getName()
					+ "'");
		}

		if (!pk.isPrimaryKey()) {
			throw new EtlRuntimeException("'" + primaryKeyAttribute + "' is not a PK in '" + dbEntity.getName() + "'");
		}

		Object id = source.get(pk.getName());
		if (id != null) {

			// 1. meaningful ID
			// TODO: must compile all this... figuring this on the fly is
			// slow
			ObjAttribute opk = entity.getAttributeForDbAttribute(pk);
			if (opk != null) {
				target.writeProperty(opk.getName(), id);
			}
			// 2. PK is auto-generated ... I guess this is sorta
			// expected to fail - generated meaningless PK should not be
			// pushed from the client
			else if (pk.isGenerated()) {
				throw new EtlRuntimeException("PK for '" + entity.getName()
						+ "' is autogenerated and is not allowed to be synced from source.");
			}
			// 3. regular PK
			else {
				target.getObjectId().getReplacementIdMap().put(pk.getName(), id);
			}
		}

		return target;
	}

	@Override
	public void update(ObjectContext context, Map<String, Object> source, T target) {
		for (Map.Entry<String, Object> e : source.entrySet()) {
			if (!e.getKey().equals(primaryKeyAttribute)) {
				writeProperty(context, target, e.getKey(), e.getValue());
			}
		}
	}
}
