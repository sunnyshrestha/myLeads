package dev.suncha.myleads;

/**
 * Created by Sunny on 5/14/2015.
 */
public class myLeads {
    int _id;
    String _com_name;
    String _com_add;
    String _com_num;
    String _com_web;
    String _per_name;
    String _designation;
    String _mobile;
    String _email;
    String _products;
    String _meeting_date;
    String _followup_date;
    String _remarks;

    public myLeads(int _id, String _com_name, String _com_add, String _com_num, String _com_web, String _per_name, String _designation, String _mobile, String _email, String _products, String _meeting_date, String _followup_date, String _remarks) {
        this._id = _id;
        this._com_name = _com_name;
        this._com_add = _com_add;
        this._com_num = _com_num;
        this._com_web = _com_web;
        this._per_name = _per_name;
        this._designation = _designation;
        this._mobile = _mobile;
        this._email = _email;
        this._products = _products;
        this._meeting_date = _meeting_date;
        this._followup_date = _followup_date;
        this._remarks = _remarks;
    }

    public String get_followup_date() {
        return _followup_date;
    }

    public void set_followup_date(String _followup_date) {
        this._followup_date = _followup_date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_com_name() {
        return _com_name;
    }

    public void set_com_name(String _com_name) {
        this._com_name = _com_name;
    }

    public String get_com_add() {
        return _com_add;
    }

    public void set_com_add(String _com_add) {
        this._com_add = _com_add;
    }

    public String get_com_num() {
        return _com_num;
    }

    public void set_com_num(String _com_num) {
        this._com_num = _com_num;
    }

    public String get_com_web() {
        return _com_web;
    }

    public void set_com_web(String _com_web) {
        this._com_web = _com_web;
    }

    public String get_per_name() {
        return _per_name;
    }

    public void set_per_name(String _per_name) {
        this._per_name = _per_name;
    }

    public String get_designation() {
        return _designation;
    }

    public void set_designation(String _designation) {
        this._designation = _designation;
    }

    public String get_mobile() {
        return _mobile;
    }

    public void set_mobile(String _mobile) {
        this._mobile = _mobile;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_products() {
        return _products;
    }

    public void set_products(String _products) {
        this._products = _products;
    }

    public String get_meeting_date() {
        return _meeting_date;
    }

    public void set_meeting_date(String _meeting_date) {
        this._meeting_date = _meeting_date;
    }

    public String get_remarks() {
        return _remarks;
    }

    public void set_remarks(String _remarks) {
        this._remarks = _remarks;
    }
}
