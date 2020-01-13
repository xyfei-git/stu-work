package cn.demo.bean;


import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

public class PageBean<T> {
	
	//总条数
	@TableField(exist = false)
	private Integer total;
	
	//总页数
	@TableField(exist = false)
	private Integer pageTotal;
	
	//每页显示条数
	@TableField(exist = false)
	private Integer pageSize = 3;
	
	//页数
	@TableField(exist = false)
	private Integer pageIndex = 1;
	
	//起始条数下标
	@TableField(exist = false)
	private Integer startIndex;
	
	//用于存放查询结果的集合
	@TableField(exist = false)
	private List<T> list;
	
	//计算总页数和起始条数下标的方法
	public void  calculate(){
		if (pageSize<1) pageSize=1;
		//计算总页数
		pageTotal = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		if (pageIndex<1 || pageIndex>pageTotal) {
			pageIndex=1;
		}
		//计算起始条数下标
		startIndex = (pageIndex - 1) * pageSize;
	}

	public Integer getTotal() {
		if (this.total==null) total=0;
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize<1) {
			pageSize=1;
		}
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		if (pageIndex<1) {
			pageIndex=1;
		}
		this.pageIndex = pageIndex;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
