package org.example.tdfinalprog3.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.*;

public class Collectivity {
    private String id;
    private String number;
    private String name;
    private String location;
    private String specialty;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    private boolean federationApproval;
    private CollectivityStructure structure;
    private List<String> memberIds;

    public Collectivity() {
        this.id = UUID.randomUUID().toString();
        this.creationDate = LocalDate.now();
        this.memberIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isFederationApproval() {
        return federationApproval;
    }

    public void setFederationApproval(boolean federationApproval) {
        this.federationApproval = federationApproval;
    }

    public CollectivityStructure getStructure() {
        return structure;
    }

    public void setStructure(CollectivityStructure structure) {
        this.structure = structure;
    }

    public List<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
    }

    public Collection<Object> getNumber() {
        return Collections.singleton(number);
    }

    public static class CollectivityStructure {
        private String presidentId;
        private String vicePresidentId;
        private String treasurerId;
        private String secretaryId;

        public String getPresidentId()
        { return presidentId; }
        public void setPresidentId(String presidentId)
        { this.presidentId = presidentId; }
        public String getVicePresidentId()
        { return vicePresidentId; }
        public void setVicePresidentId(String vicePresidentId)
        { this.vicePresidentId = vicePresidentId; }
        public String getTreasurerId()
        { return treasurerId; }
        public void setTreasurerId(String treasurerId)
        { this.treasurerId = treasurerId; }
        public String getSecretaryId()
        { return secretaryId; }
        public void setSecretaryId(String secretaryId)
        { this.secretaryId = secretaryId; }
    }
}
