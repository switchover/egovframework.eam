package egovframework.eam.admin.main.client.role;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

import egovframework.eam.admin.main.client.datasource.AbstractRestDataSource;

public class RoleHierarchyDataSource extends AbstractRestDataSource {
	private static RoleHierarchyDataSource instance = null;

	public static RoleHierarchyDataSource getInstance() {
		if (instance == null) {
			instance = new RoleHierarchyDataSource("roleHierarchyEditDS");
		}

		return instance;
	}

	private RoleHierarchyDataSource(String id) {
		super(id);

		setFetchDataURL("/service/roleHierarchies");
		setAddDataURL("/service/roleHierarchy");
		setUpdateDataURL("/service/roleHierarchy");
		setRemoveDataURL("/service/roleHierarchy");
	}

	protected void init() {
		setDataFormat(DSDataFormat.XML);
		setXmlRecordXPath("/roleHierarchies/data/*");
		
		DataSourceField parentField = new DataSourceField("parentRole", FieldType.TEXT, "Parent Role");
		//parentField.setPrimaryKey(true);
		parentField.setCanEdit(false);
		parentField.setForeignKey(id + ".childRole");
		parentField.setRootValue("root");
		
		DataSourceField childField = new DataSourceField("childRole", FieldType.TEXT, "Child Role");
		childField.setPrimaryKey(true);
		childField.setCanEdit(false);

		setFields(parentField, childField);
	}
}