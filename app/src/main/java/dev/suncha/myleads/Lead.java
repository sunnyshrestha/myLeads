package dev.suncha.myleads;

/**
 * Created by user on 7/3/2015.
 * Created as a substitute for  myLeads class in an attempt to write cleaner code
 */
public class Lead {
    int id;
    String company_name;
    String company_address;
    String company_phone;
    String	company_web;
    String	person_name;
    String person_designation;
    String person_mobile;
    String person_email;
    String product_discussed;
    String meeting_date;
    String followup_date;
    String remarks;

    //Empty Constructor
    public Lead(){

    }

    //Constructor

    public Lead(int id, String company_name, String company_address, String company_phone, String company_web, String person_name, String person_designation, String person_mobile, String person_email, String product_discussed, String meeting_date, String followup_date, String remarks) {
        this.id = id;
        this.company_name = company_name;
        this.company_address = company_address;
        this.company_phone = company_phone;
        this.company_web = company_web;
        this.person_name = person_name;
        this.person_designation = person_designation;
        this.person_mobile = person_mobile;
        this.person_email = person_email;
        this.product_discussed = product_discussed;
        this.meeting_date = meeting_date;
        this.followup_date = followup_date;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_phone() {
        return company_phone;
    }

    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone;
    }

    public String getCompany_web() {
        return company_web;
    }

    public void setCompany_web(String company_web) {
        this.company_web = company_web;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_designation() {
        return person_designation;
    }

    public void setPerson_designation(String person_designation) {
        this.person_designation = person_designation;
    }

    public String getPerson_mobile() {
        return person_mobile;
    }

    public void setPerson_mobile(String person_mobile) {
        this.person_mobile = person_mobile;
    }

    public String getPerson_email() {
        return person_email;
    }

    public void setPerson_email(String person_email) {
        this.person_email = person_email;
    }

    public String getProduct_discussed() {
        return product_discussed;
    }

    public void setProduct_discussed(String product_discussed) {
        this.product_discussed = product_discussed;
    }

    public String getMeeting_date() {
        return meeting_date;
    }

    public void setMeeting_date(String meeting_date) {
        this.meeting_date = meeting_date;
    }

    public String getFollowup_date() {
        return followup_date;
    }

    public void setFollowup_date(String followup_date) {
        this.followup_date = followup_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }




}
