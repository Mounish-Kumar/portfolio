package dev.mounish.portfolio.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;

public class PageRequestBuilder {
	
	public static PageRequest createPageRequest(final SearchRequest searchRequest) {
		PageRequest pageRequest = null;
		if(searchRequest != null) {
			int pageNumber = searchRequest.getPageNumber() > 0 ? searchRequest.getPageNumber() - 1 : 0;
			int pageSize = searchRequest.getPageSize() > 0 ? searchRequest.getPageSize() : 10;
			String sortColumn = StringUtils.hasText(searchRequest.getSortColumn()) ? searchRequest.getSortColumn() : "id";
			Direction sortOrder =  (StringUtils.hasText(searchRequest.getSortOrder()) && "DESC".equalsIgnoreCase(searchRequest.getSortOrder()))
					? Sort.Direction.DESC : Sort.Direction.ASC;
			pageRequest = PageRequest.of(pageNumber, pageSize, sortOrder, sortColumn);
		}
		return pageRequest;
	}

}
