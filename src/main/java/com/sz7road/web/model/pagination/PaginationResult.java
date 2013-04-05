package com.sz7road.web.model.pagination;

import java.util.List;

public final class PaginationResult<E> {

	private int total = 0;
	private List<E> resultList;

	/**
	 * Constructor
	 * 
	 * @param total
	 * @param result
	 */
	public PaginationResult(int total, List<E> result) {
		super();
		this.total = total;
		this.resultList = result;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @return the result
	 */
	public List<E> getResultList() {
		return resultList;
	}


}
