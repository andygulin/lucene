package com.search.lucene.ex;

import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

public class QueryParserEx extends QueryParser {

	private Map<String, Class<?>> fields;

	public QueryParserEx(Version matchVersion, String f, Analyzer a) {
		super(matchVersion, f, a);
	}

	public QueryParserEx(Version matchVersion, String f, Analyzer a, Map<String, Class<?>> fields) {
		this(matchVersion, f, a);
		this.fields = fields;
	}

	@Override
	protected Query getRangeQuery(String field, String part1, String part2, boolean inclusive) throws ParseException {
		if (fields.containsKey(field)) {
			String className = fields.get(field).getName();
			if (className.equals("java.lang.Integer")) {
				return NumericRangeQuery.newIntRange(field, NumberUtils.toInt(part1), NumberUtils.toInt(part2),
						inclusive, inclusive);
			} else if (className.equals("java.lang.Long")) {
				return NumericRangeQuery.newLongRange(field, NumberUtils.toLong(part1), NumberUtils.toLong(part2),
						inclusive, inclusive);
			} else if (className.equals("java.lang.Float")) {
				return NumericRangeQuery.newFloatRange(field, NumberUtils.toFloat(part1), NumberUtils.toFloat(part2),
						inclusive, inclusive);
			} else if (className.equals("java.lang.Double")) {
				return NumericRangeQuery.newDoubleRange(field, NumberUtils.toDouble(part1), NumberUtils.toDouble(part2),
						inclusive, inclusive);
			}
		}
		return super.newRangeQuery(field, part1, part2, inclusive);
	}

}
