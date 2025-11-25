package com.example.sweezcustoms.enums;

/**
 * Represents the types of identification documents that an individual
 * (private person) can provide within the automated customs system.
 *
 * <p>These documents are used to:
 * <ul>
 *     <li>Verify the identity of the individual submitting a customs declaration;</li>
 *     <li>Authenticate the person when accessing the system;</li>
 *     <li>Ensure compliance with legal and regulatory requirements.</li>
 * </ul>
 */
public enum IndividualDocumentType {

    /**
     * The individual's passport (national or international), used
     * for identity verification and legal compliance.
     */
    PERSON_PASSPORT,

    /**
     * A government-issued photo ID other than a passport,
     * such as a national ID card or driver's license,
     * used for identity verification.
     */
    PERSON_PHOTO_ID;
}
