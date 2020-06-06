package dev.mounish.portfolio.common;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SpecificationBuilder {
	
	public <T> Specification<T> getSpecification(final SearchRequest searchRequest) {
		return (root, query, cb) -> {
			Predicate finalSpec  = cb.conjunction();
			if(searchRequest != null && searchRequest.getQueries() != null && !searchRequest.getQueries().isEmpty()) {
				Predicate current = null;
				for(QueryParam queryParam : searchRequest.getQueries()) {
					if(StringUtils.hasText(queryParam.getColumnName()) && StringUtils.hasText(queryParam.getValue())) {
						final String columnName = queryParam.getColumnName().trim();
						final String value = queryParam.getValue().trim();
						try {
							if(current == null) {
								current = cb.equal(root.get(columnName), value);
							} else {
								current = cb.and(current, cb.equal(root.get(columnName), value));
							}
						} catch(Exception e) {
							throw new PortfolioException(PortfolioErrorMessage.INVALID_COLUMN.getMessage() + " : " + columnName);
						}
					}
				}
				finalSpec = cb.and(finalSpec, current);
			}
			return finalSpec;
		};
	}

}
