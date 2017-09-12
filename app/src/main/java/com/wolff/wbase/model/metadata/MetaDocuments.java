package com.wolff.wbase.model.metadata;

/**
 * Created by wolff on 06.09.2017.
 */

public class MetaDocuments {
    public static final class MDocument {
        public static final class HEAD{
            public static final String DATE = "Date";
            public static final String NUMBER= "Number";
            public static final String POSTED= "Posted";

        }
    }
    public static final class MDoc_Kassa_PKO{
        public static final String DOCUMENT_NAME = "Document_Касса_ПКО";

        public static final class HEAD{
            public static final String ORGANIZATION_KEY = "Организация_Key";
            public static final String CURRENCY_KEY = "Валюта_Key";
            public static final String CONTRAGENT_KEY = "Контрагент";
            public static final String CONTRAGENT_TYPE = "Контрагент_Type";
            public static final String DOGOVOR_KEY = "Договор_Key";
            public static final String CURRENCY_COURSE = "КурсВалюты";
            public static final String SUMMA_VAL = "СуммаВал";
            public static final String ACCOUNT_KEY = "Организация_БанковскийСчет_Key";

        }
    }
}
