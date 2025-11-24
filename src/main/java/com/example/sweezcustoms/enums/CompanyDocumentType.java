package com.example.sweezcustoms.enums;

/**
 * Represents the different types of company-related documents
 * that can be uploaded and stored within the automated customs system.
 *
 * <p>This enum defines all standard document categories required for:
 * <ul>
 *     <li>company registration;</li>
 *     <li>verification of legal status;</li>
 *     <li>import/export licensing;</li>
 *     <li>tax and VAT compliance;</li>
 *     <li>authorization of company representatives.</li>
 * </ul>
 *
 * <p>Each document type corresponds to a specific legal or regulatory
 * requirement used in customs declarations and company validation workflows.</p>
 *
 * <p><b>Usage example:</b></p>
 * <pre>
 * CompanyDocument document = new CompanyDocument();
 * document.setDocumentType(CompanyDocumentType.COMPANY_REG_CERTIFICATE);
 * </pre>
 */
public enum CompanyDocumentType {
    /**
     * State registration certificate confirming the company
     * is officially registered as a legal entity.
     */
     COMPANY_REG_CERTIFICATE,

    /**
     * Certificate containing the company's tax identification number (INN/TIN).
     * Used to verify tax registration status.
     */
     COMPANY_INN_CERTIFICATE,

    /**
     * The company's charter (Устав компании), defining its structure,
     * ownership, and legal operational boundaries.
     */
     COMPANY_CHARTER,

    /**
     * Official government-issued license that allows the company
     * to import goods into the country.
     */
     COMPANY_LICENSE_IMPORT,

    /**
     * Official license that permits the company to export goods
     * outside the country.
     */
     COMPANY_LICENSE_EXPORT,

    /**
     * Confirmation of registration with the tax authority,
     * typically proving the company is recognized as a taxpayer.
     */
     COMPANY_TAX_REGISTRATION,

    /**
     * Certificate confirming the company's VAT registration status.
     */
     COMPANY_VAT_CERTIFICATE,

    /**
     * Power of attorney that authorizes a company representative
     * to act on behalf of the company in customs procedures.
     */
     COMPANY_POWER_OF_ATTORNEY;
}
