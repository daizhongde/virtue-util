package person.daizhongde.virtue.util.net.http;

import java.sql.Timestamp;

/**
 * TAuthorityEmployee entity. @author MyEclipse Persistence Tools
 */

public class TAuthorityEmployee implements java.io.Serializable {

	// Fields

	private String employee_number;
	private String sbu_id;
	private String sbu;
	private String company_id;
	private String company;
	private Integer organization_id;
	private String org_name;
	private String office;
	private String pager;
	private Integer person_id;
	private String first_name;
	private String last_name;
	private String full_name;
	private String email_address;
	private Integer age;
	private Integer assignment_id;
	private String birth_date;
	private String class_;
	private String working_location;
	private String seat_no;
	private String mobile;
	private String nt_account;
	private Integer supervisor_id;
	private String supervisor_name;
	private String highest_degree;
	private String hire_date;

	// Constructors

	/** default constructor */
	public TAuthorityEmployee() {
	}

	/** minimal constructor */
	public TAuthorityEmployee(String employee_number) {
		this.employee_number = employee_number;
	}

	/** full constructor */
	public TAuthorityEmployee(String employee_number, String sbu_id,
			String sbu, String company_id, String company,
			Integer organization_id, String org_name, String office,
			String pager, Integer person_id, String first_name,
			String last_name, String full_name, String email_address,
			Integer age, Integer assignment_id, String birth_date,
			String class_, String working_location, String seat_no,
			String mobile, String nt_account, Integer supervisor_id,
			String supervisor_name, String highest_degree, String hire_date) {
		this.employee_number = employee_number;
		this.sbu_id = sbu_id;
		this.sbu = sbu;
		this.company_id = company_id;
		this.company = company;
		this.organization_id = organization_id;
		this.org_name = org_name;
		this.office = office;
		this.pager = pager;
		this.person_id = person_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.full_name = full_name;
		this.email_address = email_address;
		this.age = age;
		this.assignment_id = assignment_id;
		this.birth_date = birth_date;
		this.class_ = class_;
		this.working_location = working_location;
		this.seat_no = seat_no;
		this.mobile = mobile;
		this.nt_account = nt_account;
		this.supervisor_id = supervisor_id;
		this.supervisor_name = supervisor_name;
		this.highest_degree = highest_degree;
		this.hire_date = hire_date;
	}

	// Property accessors

	public String getEmployee_number() {
		return this.employee_number;
	}

	public void setEmployee_number(String employee_number) {
		this.employee_number = employee_number;
	}

	public String getSbu_id() {
		return this.sbu_id;
	}

	public void setSbu_id(String sbu_id) {
		this.sbu_id = sbu_id;
	}

	public String getSbu() {
		return this.sbu;
	}

	public void setSbu(String sbu) {
		this.sbu = sbu;
	}

	public String getCompany_id() {
		return this.company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getOrganization_id() {
		return this.organization_id;
	}

	public void setOrganization_id(Integer organization_id) {
		this.organization_id = organization_id;
	}

	public String getOrg_name() {
		return this.org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getPager() {
		return this.pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public Integer getPerson_id() {
		return this.person_id;
	}

	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}

	public String getFirst_name() {
		return this.first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return this.last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFull_name() {
		return this.full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEmail_address() {
		return this.email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAssignment_id() {
		return this.assignment_id;
	}

	public void setAssignment_id(Integer assignment_id) {
		this.assignment_id = assignment_id;
	}

	public String getBirth_date() {
		return this.birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public String getWorking_location() {
		return this.working_location;
	}

	public void setWorking_location(String working_location) {
		this.working_location = working_location;
	}

	public String getSeat_no() {
		return this.seat_no;
	}

	public void setSeat_no(String seat_no) {
		this.seat_no = seat_no;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNt_account() {
		return this.nt_account;
	}

	public void setNt_account(String nt_account) {
		this.nt_account = nt_account;
	}

	public Integer getSupervisor_id() {
		return this.supervisor_id;
	}

	public void setSupervisor_id(Integer supervisor_id) {
		this.supervisor_id = supervisor_id;
	}

	public String getSupervisor_name() {
		return this.supervisor_name;
	}

	public void setSupervisor_name(String supervisor_name) {
		this.supervisor_name = supervisor_name;
	}

	public String getHighest_degree() {
		return this.highest_degree;
	}

	public void setHighest_degree(String highest_degree) {
		this.highest_degree = highest_degree;
	}

	public String getHire_date() {
		return this.hire_date;
	}

	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}

}