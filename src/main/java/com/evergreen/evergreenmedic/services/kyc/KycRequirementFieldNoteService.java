package com.evergreen.evergreenmedic.services.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycRequirementField;
import com.evergreen.evergreenmedic.entities.kyc.KycRequirementFieldNote;
import com.evergreen.evergreenmedic.repositories.kyc.KycRequirementFieldNoteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KycRequirementFieldNoteService {


    private final KycRequirementFieldNoteRepository kycRequirementFieldNoteRepository;

    public KycRequirementFieldNoteService(KycRequirementFieldNoteRepository kycRequirementFieldNoteRepository) {
        this.kycRequirementFieldNoteRepository = kycRequirementFieldNoteRepository;
    }

    public List<KycRequirementFieldNote> getFirstNameNotes(KycRequirementField kycRequirementField) {

        List<KycRequirementFieldNote> notes = new ArrayList<KycRequirementFieldNote>();
        KycRequirementFieldNote KycRequirementFieldNote1 = new KycRequirementFieldNote();
        KycRequirementFieldNote1.setNote("The first name is required.");
        KycRequirementFieldNote1.setKycRequirementField(kycRequirementField);
        notes.add(KycRequirementFieldNote1);

        KycRequirementFieldNote KycRequirementFieldNote2 = new KycRequirementFieldNote();
        KycRequirementFieldNote2.setNote("The first name must match the name on the proof document.");
        KycRequirementFieldNote2.setKycRequirementField(kycRequirementField);
        notes.add(KycRequirementFieldNote2);


        KycRequirementFieldNote KycRequirementFieldNote3 = new KycRequirementFieldNote();
        KycRequirementFieldNote3.setNote("The first name should not contain any special characters.");
        KycRequirementFieldNote3.setKycRequirementField(kycRequirementField);
        notes.add(KycRequirementFieldNote3);


        return kycRequirementFieldNoteRepository.saveAll(notes);

    }

    public List<KycRequirementFieldNote> getLastNameNotes(KycRequirementField kycRequirementField) {

        List<KycRequirementFieldNote> notes = new ArrayList<KycRequirementFieldNote>();
        KycRequirementFieldNote KycRequirementFieldNote1 = new KycRequirementFieldNote();
        KycRequirementFieldNote1.setNote("The last name is required.");
        KycRequirementFieldNote1.setKycRequirementField(kycRequirementField);
        notes.add(KycRequirementFieldNote1);

        KycRequirementFieldNote KycRequirementFieldNote2 = new KycRequirementFieldNote();
        KycRequirementFieldNote2.setNote("The last name must match the name on the proof document.");
        KycRequirementFieldNote2.setKycRequirementField(kycRequirementField);
        notes.add(KycRequirementFieldNote2);


        KycRequirementFieldNote KycRequirementFieldNote3 = new KycRequirementFieldNote();
        KycRequirementFieldNote3.setNote("The last name should not contain any special characters.");
        KycRequirementFieldNote3.setKycRequirementField(kycRequirementField);
        notes.add(KycRequirementFieldNote3);


        return kycRequirementFieldNoteRepository.saveAll(notes);

    }

    public List<KycRequirementFieldNote> getProofDocNotes(KycRequirementField kycRequirementField) {

        List<KycRequirementFieldNote> notes = new ArrayList<>();

        KycRequirementFieldNote note1 = new KycRequirementFieldNote();
        note1.setNote("The document should either be a CNIC or a birth certificate.");
        note1.setKycRequirementField(kycRequirementField);
        notes.add(note1);

        KycRequirementFieldNote note2 = new KycRequirementFieldNote();
        note2.setNote("Please provide a clear image of the document.");
        note2.setKycRequirementField(kycRequirementField);
        notes.add(note2);

        KycRequirementFieldNote note3 = new KycRequirementFieldNote();
        note3.setNote("The document must be in one of the following formats: PNG or JPEG.");
        note3.setKycRequirementField(kycRequirementField);
        notes.add(note3);

        KycRequirementFieldNote note4 = new KycRequirementFieldNote();
        note4.setNote("The document image size should be between 1MB and 2MB.");
        note4.setKycRequirementField(kycRequirementField);
        notes.add(note4);

        return kycRequirementFieldNoteRepository.saveAll(notes);
    }

    public List<KycRequirementFieldNote> getEmailNotes(KycRequirementField kycRequirementField) {

        List<KycRequirementFieldNote> notes = new ArrayList<>();

        KycRequirementFieldNote note1 = new KycRequirementFieldNote();
        note1.setNote("The email should be your account registration email.");
        note1.setKycRequirementField(kycRequirementField);
        notes.add(note1);


        return kycRequirementFieldNoteRepository.saveAll(notes);
    }

    public List<KycRequirementFieldNote> getPhoneNotes(KycRequirementField kycRequirementField) {

        List<KycRequirementFieldNote> notes = new ArrayList<>();

        KycRequirementFieldNote note1 = new KycRequirementFieldNote();
        note1.setNote("Please make sure the phone number is valid.");
        note1.setKycRequirementField(kycRequirementField);
        notes.add(note1);


        return kycRequirementFieldNoteRepository.saveAll(notes);
    }

}
