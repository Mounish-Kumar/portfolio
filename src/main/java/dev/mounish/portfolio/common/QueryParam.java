package dev.mounish.portfolio.common;

public class QueryParam {

	private String columnName;
	
	private String value;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "QueryParam [columnName=" + columnName + ", value=" + value + "]";
	}
	
	
}
