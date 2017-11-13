package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * @author customer
 *
 * @param <T>
 */
public class Page<T> {
	public static final Integer SIZE = 5;
	private Integer index; //每页的起始下标
	private Integer totalPage; //总页数
	private Integer totalCount; //总记录数
	private List<T> list = new ArrayList<T>();
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		if(index <= 1){
			this.index = 1;
		}else if(index >= this.totalPage){
			this.index = this.totalPage;
		}else{
			this.index = index;
		}
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		if(totalPage  == 0){
			this.totalPage = 1;
		}else if(totalPage > this.totalPage){
			this.totalPage = totalPage;
		}
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		if(totalCount > 0){
			this.totalCount = totalCount;
			this.totalPage = this.totalCount % SIZE == 0 ?
					this.totalCount / SIZE : this.totalCount / SIZE + 1;
		}
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
