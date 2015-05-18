package dev.suncha.myleads;

/**
 * Created by Sunny on 5/14/2015.
 */
public class myLeads {
    int id;
    String ofic_name;
    String ofic_add;
    String ofic_num;
    String web;
    String per_name;
    String designation;
    String mobile;
    String email;
    String products;
    String meeting_date;
    String followup_date;
    String remarks;

    public myLeads(int id, String ofic_name, String ofic_add, String ofic_num, String web, String per_name, String designation, String mobile, String email, String products, String meeting_date, String followup_date, String remarks) {
        this.id = id;
        this.ofic_name = ofic_name;
        this.ofic_add = ofic_add;
        this.ofic_num = ofic_num;
        this.web = web;
        this.per_name = per_name;
        this.designation = designation;
        this.mobile = mobile;
        this.email = email;
        this.products = products;
        this.meeting_date = meeting_date;
        this.followup_date = followup_date;
        this.remarks = remarks;
    }

    public myLeads(String ofic_name, String ofic_add, String ofic_num, String web, String per_name, String designation, String mobile, String email, String products, String meeting_date, String followup_date, String remarks) {
        this.ofic_name = ofic_name;
        this.ofic_add = ofic_add;
        this.ofic_num = ofic_num;
        this.web = web;
        this.per_name = per_name;
        this.designation = designation;
        this.mobile = mobile;
        this.email = email;
        this.products = products;
        this.meeting_date = meeting_date;
        this.followup_date = followup_date;
        this.remarks = remarks;
    }


    public myLeads() {
    }


    public String getfollowup_date() {
        return followup_date;
    }

    public void setfollowup_date(String followup_date) {
        this.followup_date = followup_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOfic_name() {
        return ofic_name;
    }

    public void setOfic_name(String ofic_name) {
        this.ofic_name = ofic_name;
    }

    public String getOfic_add() {
        return ofic_add;
    }

    public void setOfic_add(String ofic_add) {
        this.ofic_add = ofic_add;
    }

    public String getOfic_num() {
        return ofic_num;
    }

    public void setOfic_num(String ofic_num) {
        this.ofic_num = ofic_num;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPer_name() {
        return per_name;
    }

    public void setPer_name(String per_name) {
        this.per_name = per_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getMeeting_date() {
        return meeting_date;
    }

    public void setMeeting_date(String meeting_date) {
        this.meeting_date = meeting_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
