package org.example.tdfinalprog3.model;

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

    private String gender;
    private String address;
    private String profession;
    private String phoneNumber;
    private String email;
    private String occupation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate adhesionDate;

    private String collectivityId;

    // Champs manquants ajoutés
    private boolean registrationFeePaid;
    private boolean membershipDuesPaid;
    private List<String> refereeIds;
    private List<RefereeRelation> refereeRelations;

    public Member() {
        this.id = UUID.randomUUID().toString();
        this.adhesionDate = LocalDate.now();
        this.refereeIds = new ArrayList<>();
        this.refereeRelations = new ArrayList<>();
        this.registrationFeePaid = false;
        this.membershipDuesPaid = false;
    }

    // ========== Getters et Setters existants ==========

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public LocalDate getAdhesionDate() {
        return adhesionDate;
    }

    public void setAdhesionDate(LocalDate adhesionDate) {
        this.adhesionDate = adhesionDate;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    // ========== Nouveaux Getters et Setters ==========

    public boolean isRegistrationFeePaid() {
        return registrationFeePaid;
    }

    public void setRegistrationFeePaid(boolean registrationFeePaid) {
        this.registrationFeePaid = registrationFeePaid;
    }

    public boolean isMembershipDuesPaid() {
        return membershipDuesPaid;
    }

    public void setMembershipDuesPaid(boolean membershipDuesPaid) {
        this.membershipDuesPaid = membershipDuesPaid;
    }

    public List<String> getRefereeIds() {
        return refereeIds;
    }

    public void setRefereeIds(List<String> refereeIds) {
        this.refereeIds = refereeIds;
    }

    public List<RefereeRelation> getRefereeRelations() {
        return refereeRelations;
    }

    public void setRefereeRelations(List<RefereeRelation> refereeRelations) {
        this.refereeRelations = refereeRelations;
    }

    // ========== Méthodes utilitaires ==========

    /**
     * Ajoute un parrain avec sa relation
     */
    public void addReferee(String refereeId, String relationship) {
        if (!this.refereeIds.contains(refereeId)) {
            this.refereeIds.add(refereeId);
        }
        this.refereeRelations.add(new RefereeRelation(refereeId, relationship));
    }

    /**
     * Vérifie si le membre est un membre confirmé (SENIOR ou poste spécifique)
     */
    public boolean isConfirmedMember() {
        return occupation != null && (
                occupation.equals("SENIOR") ||
                        occupation.equals("PRESIDENT") ||
                        occupation.equals("VICE_PRESIDENT") ||
                        occupation.equals("TREASURER") ||
                        occupation.equals("SECRETARY")
        );
    }

    /**
     * Vérifie si le membre est junior
     */
    public boolean isJunior() {
        return occupation != null && occupation.equals("JUNIOR");
    }

    /**
     * Vérifie si le membre a payé tous ses frais
     */
    public boolean hasPaidAllFees() {
        return registrationFeePaid && membershipDuesPaid;
    }

    /**
     * Calcule l'ancienneté en jours
     */
    public long getSeniorityDays() {
        if (adhesionDate == null) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(adhesionDate, LocalDate.now());
    }

    /**
     * Vérifie si le membre a au moins X jours d'ancienneté
     */
    public boolean hasMinimumSeniority(int days) {
        return getSeniorityDays() >= days;
    }

    // ========== Classe interne pour les relations de parrainage ==========

    public static class RefereeRelation {
        private String refereeId;
        private String relationship;

        public RefereeRelation() {}

        public RefereeRelation(String refereeId, String relationship) {
            this.refereeId = refereeId;
            this.relationship = relationship;
        }

        public String getRefereeId() {
            return refereeId;
        }

        public void setRefereeId(String refereeId) {
            this.refereeId = refereeId;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }
    }

    // ========== toString ==========

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", occupation='" + occupation + '\'' +
                ", collectivityId='" + collectivityId + '\'' +
                '}';
    }
}