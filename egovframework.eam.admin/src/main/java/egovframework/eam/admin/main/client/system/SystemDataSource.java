package egovframework.eam.admin.main.client.system;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

import egovframework.eam.admin.main.client.datasource.AbstractRestDataSource;

public class SystemDataSource extends AbstractRestDataSource {
	private static SystemDataSource instance = null;

	public static SystemDataSource getInstance() {
		if (instance == null) {
			instance = new SystemDataSource("systemEditDS");
		}

		return instance;
	}

	private SystemDataSource(String id) {
		super(id);

		setFetchDataURL("/service/systems");
		setAddDataURL("/service/system");
		setUpdateDataURL("/service/system");
		setRemoveDataURL("/service/system");
	}

	protected void init() {
		setDataFormat(DSDataFormat.XML);
		setXmlRecordXPath("/systems/data/*");
		
		DataSourceField idField = new DataSourceField("systemId", FieldType.TEXT, "System ID");
		idField.setPrimaryKey(true);
		idField.setCanEdit(false);
		DataSourceField nameField = new DataSourceField("systemName", FieldType.TEXT, "System Name");
		DataSourceField passwordField = new DataSourceField("password", FieldType.TEXT, "Resource Pattern");
		DataSourceField createdDateField = new DataSourceField("createdDatetime", FieldType.TEXT, "Created Date & Time");
		DataSourceField ipField = new DataSourceField("ip", FieldType.TEXT, "System IP");
		
		ipField.setValueXPath("ipList/ip");

		setFields(idField, nameField, passwordField, createdDateField, ipField);
	}
}