package mx.com.hunkabann.skf.frontent.util.searching;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.trg.search.Filter;
public class SearchOperators implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient int searchOperatorId;
	private transient String searchOperatorSign;
	private transient String searchOperatorName;

	/**
	 * default constructor.<br>
	 */
	public SearchOperators() {
	}

	/**
	 * constructor.<br>
	 * 
	 * @param searchOperatorId
	 * @param searchOperatorSign
	 * @param searchOperatorName
	 */
	public SearchOperators(int searchOperatorId, String searchOperatorSign, String searchOperatorName) {
		this.searchOperatorId = searchOperatorId;
		this.searchOperatorSign = searchOperatorSign;
		this.searchOperatorName = searchOperatorName;
	}

	public List<SearchOperators> getAllOperators() {

		List<SearchOperators> result = new ArrayList<SearchOperators>();

		// list position 0
		result.add(new SearchOperators(-1, "", "no operator"));
		// list position 1
		result.add(new SearchOperators(Filter.OP_EQUAL, "=", "equals"));
		// list position 2
		result.add(new SearchOperators(Filter.OP_NOT_EQUAL, "#", "not equal"));
		// list position 3
		result.add(new SearchOperators(Filter.OP_LESS_THAN, "<", "less than"));
		// list position 4
		result.add(new SearchOperators(Filter.OP_GREATER_THAN, ">", "greater than"));
		// list position 5
		result.add(new SearchOperators(Filter.OP_LESS_OR_EQUAL, "<=", "less or equal"));
		// list position 6
		result.add(new SearchOperators(Filter.OP_GREATER_OR_EQUAL, ">=", "greater or equal"));
		// list position 7
		result.add(new SearchOperators(Filter.OP_ILIKE, "~", "ilike"));

		return result;
	}

	//@Override
	public int hashCode() {
		return Integer.valueOf(getSearchOperatorId()).hashCode();
	}

	public boolean equals(SearchOperators searchOperators) {
		return getSearchOperatorId() == searchOperators.getSearchOperatorId();
	}

	//@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof SearchOperators) {
			SearchOperators searchOperators = (SearchOperators) obj;
			return equals(searchOperators);
		}

		return false;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// ++++++++++++++++++ getter / setter +++++++++++++++++++//
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

	public void setSearchOperatorId(int searchOperatorId) {
		this.searchOperatorId = searchOperatorId;
	}

	public int getSearchOperatorId() {
		return searchOperatorId;
	}

	public void setSearchOperatorSign(String searchOperatorSign) {
		this.searchOperatorSign = searchOperatorSign;
	}

	public String getSearchOperatorSign() {
		return searchOperatorSign;
	}

	public void setSearchOperatorName(String searchOperatorName) {
		this.searchOperatorName = searchOperatorName;
	}

	public String getSearchOperatorName() {
		return searchOperatorName;
	}

}
