package at.fickl.clubadmin.service.dto;

import java.io.Serializable;
import at.fickl.clubadmin.domain.enumeration.Sex;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Member entity. This class is used in MemberResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /members?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MemberCriteria implements Serializable {
    /**
     * Class for filtering Sex
     */
    public static class SexFilter extends Filter<Sex> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter title;

    private SexFilter sex;

    private LocalDateFilter birthdate;

    private StringFilter email;

    private StringFilter phoneNumber;

    private LocalDateFilter entryDate;

    private LocalDateFilter terminationDate;

    private LocalDateFilter exitDate;

    private StringFilter streetAddress;

    private StringFilter postalCode;

    private StringFilter city;

    private StringFilter province;

    private StringFilter country;

    private BooleanFilter vote;

    private BooleanFilter oerv;

    private StringFilter comment;

    public MemberCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public SexFilter getSex() {
        return sex;
    }

    public void setSex(SexFilter sex) {
        this.sex = sex;
    }

    public LocalDateFilter getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateFilter birthdate) {
        this.birthdate = birthdate;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateFilter getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateFilter entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateFilter getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDateFilter terminationDate) {
        this.terminationDate = terminationDate;
    }

    public LocalDateFilter getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDateFilter exitDate) {
        this.exitDate = exitDate;
    }

    public StringFilter getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(StringFilter streetAddress) {
        this.streetAddress = streetAddress;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getProvince() {
        return province;
    }

    public void setProvince(StringFilter province) {
        this.province = province;
    }

    public StringFilter getCountry() {
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public BooleanFilter getVote() {
        return vote;
    }

    public void setVote(BooleanFilter vote) {
        this.vote = vote;
    }

    public BooleanFilter getOerv() {
        return oerv;
    }

    public void setOerv(BooleanFilter oerv) {
        this.oerv = oerv;
    }

    public StringFilter getComment() {
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "MemberCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (sex != null ? "sex=" + sex + ", " : "") +
                (birthdate != null ? "birthdate=" + birthdate + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (entryDate != null ? "entryDate=" + entryDate + ", " : "") +
                (terminationDate != null ? "terminationDate=" + terminationDate + ", " : "") +
                (exitDate != null ? "exitDate=" + exitDate + ", " : "") +
                (streetAddress != null ? "streetAddress=" + streetAddress + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (province != null ? "province=" + province + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
                (vote != null ? "vote=" + vote + ", " : "") +
                (oerv != null ? "oerv=" + oerv + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
            "}";
    }

}
