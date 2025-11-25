package com.example.sweezcustoms.enums;

/**
 * Represents all possible document types that can be attached to a customs
 * declaration within the automated customs processing system.
 *
 * <p>The document categories are grouped into four logical segments:
 * <ul>
 *     <li><b>Product documents</b> – certificates and technical sheets confirming product origin, quality, conformity, and manufacturing.</li>
 *     <li><b>Transport documents</b> – logistics-related documents used during shipment and transportation.</li>
 *     <li><b>Financial documents</b> – invoices, contracts, payment records, and packing lists related to the shipment's value.</li>
 *     <li><b>Customs documents</b> – official forms, permissions, and duty calculations required for customs clearance.</li>
 * </ul>
 *
 * <p>Each document type corresponds to a specific regulatory step in the
 * declaration review workflow and may be mandatory depending on the
 * shipment type, country of origin, and customs procedure.</p>
 */
public enum DeclarationDocumentType {

    // ---------------------- PRODUCT DOCUMENTS ----------------------

    /**
     * Certificate proving the origin of the product (e.g., Form A, EUR.1).
     * Required to determine preferential duty rates and confirm country of origin.
     */
    PRODUCT_CERTIFICATE_ORIGIN,

    /**
     * Certificate confirming the quality of the goods, often issued by
     * a laboratory or certification authority.
     */
     PRODUCT_QUALITY_CERTIFICATE,

    /**
     * Technical sheet or specification describing the product's
     * characteristics, materials, and technical parameters.
     * Common for electronics, machinery, chemicals, etc.
     */
     PRODUCT_TECH_SHEET,


    /**
     * Certificate of conformity indicating that the product meets
     * national or international standards (e.g. ISO, CE).
     */
     PRODUCT_CONFORMITY_CERTIFICATE,

    /**
     * Certificate issued by the manufacturer confirming that the product
     * was produced at a specific factory and matches the declared description.
     */
     PRODUCT_MANUFACTURER_CERTIFICATE,


    // ---------------------- TRANSPORT DOCUMENTS ----------------------

    /**
     * CMR document used for international road transportation.
     * Confirms the terms of shipment and cargo details.
     */
     TRANSPORT_CMR,

    /**
     * Bill of Lading (B/L), used for sea freight.
     * Serves as a receipt, contract of carriage, and title document.
     */
     TRANSPORT_BILL_OF_LADING,

    /**
     * Air Waybill (AWB), used in air cargo transportation.
     * Contains shipment routing and carrier information.
     */
     TRANSPORT_AIRWAY_BILL,

    /**
     * Insurance policy covering the transported goods during transit.
     */
     TRANSPORT_INSURANCE,

    // ---------------------- FINANCIAL DOCUMENTS ----------------------


    /**
     * Commercial invoice listing products, quantities, and prices.
     * Used as the primary financial document in customs valuation.
     */
     FIN_INVOICE,

    /**
     * Proforma invoice issued before shipment, typically for prepayment
     * or quotation purposes.
     */
     FIN_PROFORMA_INVOICE,

    /**
     * Contract or agreement between the buyer and seller defining
     * the terms of the transaction.
     */
     FIN_CONTRACT,

    /**
     * Packing list describing the contents of the shipment:
     * weight, dimensions, number of boxes, pallets, etc.
     */
     FIN_PACKING_LIST,

    /**
     * Payment receipt or bank confirmation showing that the buyer
     * has paid for the goods, used during value verification.
     */
     FIN_PAYMENT_RECEIPT,

    // ---------------------- CUSTOMS DOCUMENTS ----------------------

    /**
     * Previous or accompanying customs declaration relevant to the shipment.
     */
     CUSTOMS_DECLARATION,

    /**
     * EX-1 export declaration used in EU customs procedures.
     */
     CUSTOMS_EX1,

    /**
     * Official permission issued by customs authorities allowing
     * the goods to be imported.
     */
     CUSTOMS_IMPORT_PERMISSION,

    /**
     * Permission authorizing export of goods.
     */
     CUSTOMS_EXPORT_PERMISSION,

    /**
     * Customs duty and tax calculation document used for determining
     * payable amounts during import clearance.
     */
     CUSTOMS_DUTY_CALCULATION;
}
