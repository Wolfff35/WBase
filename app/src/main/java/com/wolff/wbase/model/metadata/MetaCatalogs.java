package com.wolff.wbase.model.metadata;

/**
 * Created by wolff on 06.09.2017.
 */

public class MetaCatalogs {
    public static final class MObject {
        public static final class HEAD{
            public static final String REF_KEY = "Ref_Key";
            public static final String DELETION_MARK = "DeletionMark";
            public static final String DATA_VERSION = "DataVersion";

        }
    }
    public static final class MCatalog {
        public static final class HEAD{
            public static final String CODE = "Code";
            public static final String DESCRIPTION= "Description";
            public static final String ISFOLDER= "IsFolder";
            public static final String PARENT_KEY= "Parent_Key";

        }

    }

    public static final class MOrganization {
        public static final String CATALOG_NAME = "Catalog_Организации";
        public static final class HEAD{
            public static final String CONTRAGENT_KEY = "Контрагент_Key";
            public static final String PREFIX = "Префикс";

        }
    }
    public static final class MContragent {
        public static final String CATALOG_NAME = "Catalog_Контрагенты";
        public static final class HEAD{
            public static final String NAME_SHORT = "Наименование_Краткое";
            public static final String NAME_FULL = "Наименование_Полное";

        }
    }
    public static final class MCurrency{
        public static final String CATALOG_NAME = "Catalog_Валюты";
     }
    public static final class MDDS{
        public static final String CATALOG_NAME = "Catalog_СтатьиДДС";
    }
    public static final class MPodrazdelenie{
        public static final String CATALOG_NAME = "Catalog_Холдинг_Подразделения";
    }

    public static final class MDogovor {
        public static final String CATALOG_NAME = "Catalog_Договора";
        public static final class HEAD {
            public static final String CONTRAGENT_KEY = "Контрагент_Key";
            public static final String ORGANIZATION_KEY = "Организация_Key";
            //public static final String CURRENCY_KEY = "ВалютаДоговора_Key";
            //public static final String KINDOFACTIVITY_KEY = "ВидДеятельности_Key";
            public static final String NUMBER_DOG = "НомерДоговора";
            public static final String DATE_DOG = "ДатаДоговора";
        }
        public static final class Currency_Table{
            public static final String TABLE_NAME = "Валюты";
            public static final class TABLE{
                public static final String CURRENCY_DOG = "ВалютаДоговора_Key";
            }

        }
        public static final class KindOfActivity_Table{
            public static final String TABLE_NAME = "ВидыДеятельности";
            public static final class TABLE{
                public static final String KINDOFACTIVITY = "ВидДеятельности_Key";
                public static final String DDS = "СтатьяДДС_Key";
            }

        }
    }
    public static final class MBankAccount {
        public static final String CATALOG_NAME = "Catalog_Организации_БанковскиеСчета";

        public static final class HEAD {
            public static final String ORG_OWNER_KEY = "Owner_Key";
            public static final String ISCASH = "фНаличный";
            public static final String ISBLACK = "фЧерный";
        }
    }
    public static final class MAZS{
        public static final String CATALOG_NAME = "Catalog_АЗС";
    }
    public static final class MKindOfActivity {
        public static final String CATALOG_NAME = "Catalog_Холдинг_ВидыДеятельности";
        public static final class HEAD {
            public static final String IS_VZAIMORASCH = "фВзаиморасчеты";
            public static final String IS_AVANS = "фВзаиморасчеты_Авансы";
            public static final String IS_BONUS = "фВзаиморасчеты_Бонусы";
            public static final String IS_VZAIMORASCH_ZATRATA = "фВзаиморасчеты_Затраты";
        }
    }

}
//https://developer.android.com/guide/components/tasks-and-back-stack.html