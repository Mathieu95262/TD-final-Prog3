
package org.example.tdfinalprog3.model;

import org.example.tdfinalprog3.model.enums.Gender;
import org.example.tdfinalprog3.model.enums.MemberOccupation;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Member {
    private String id;
    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Gender gender;
    private String address;
    private String profession;
    private String phoneNumber;
    private String email;
    private MemberOccupation occupation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate adhesionDate;

    private String collectivityId;
    private List<String> refereeIds;
    private boolean registrationFeePaid;
    private boolean membershipDuesPaid;
    private List<RefereeRelation> refereeRelations;

    public Member() {
        this.id = UUID.randomUUID().toString();
        this.adhesionDate = LocalDate.now();
        this.refereeIds = new ArrayList<>();
        this.refereeRelations = new ArrayList<>();
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public MemberOccupation getOccupation() { return occupation; }
    public void setOccupation(MemberOccupation occupation) { this.occupation = occupation; }
    public LocalDate getAdhesionDate() { return adhesionDate; }
    public void setAdhesionDate(LocalDate adhesionDate) { this.adhesionDate = adhesionDate; }
    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }
    public List<String> getRefereeIds() { return refereeIds; }
    public void setRefereeIds(List<String> refereeIds) { this.refereeIds = refereeIds; }
    public boolean isRegistrationFeePaid() { return registrationFeePaid; }
    public void setRegistrationFeePaid(boolean registrationFeePaid) { this.registrationFeePaid = registrationFeePaid; }
    public boolean isMembershipDuesPaid() { return membershipDuesPaid; }
    public void setMembershipDuesPaid(boolean membershipDuesPaid) { this.membershipDuesPaid = membershipDuesPaid; }
    public List<RefereeRelation> getRefereeRelations() { return refereeRelations; }
    public void setRefereeRelations(List<RefereeRelation> refereeRelations) { this.refereeRelations = refereeRelations; }

    public static class RefereeRelation {
        private String refereeId;
        private String relationship;

        public RefereeRelation() {}

        public RefereeRelation(String refereeId, String relationship) {
            this.refereeId = refereeId;
            this.relationship = relationship;
        }

        public String getRefereeId() { return refereeId; }
        public void setRefereeId(String refereeId) { this.refereeId = refereeId; }
        public String getRelationship() { return relationship; }
        public void setRelationship(String relationship) { this.relationship = relationship; }
    }
}