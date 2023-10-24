package tech.ailef.dbadmin.external.dbmapping.fields;
import java.util.List;

import tech.ailef.dbadmin.external.dto.CompareOperator;

public class LongFieldType extends DbFieldType {
	@Override
	public String getFragmentName() {
		return "number";
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		return Long.parseLong(value.toString());
	}

	@Override
	public Class<?> getJavaClass() {
		return Long.class;
	}
	
	@Override
	public List<CompareOperator> getCompareOperators() {
		return List.of(CompareOperator.GT, CompareOperator.EQ, CompareOperator.LT);
	}
}