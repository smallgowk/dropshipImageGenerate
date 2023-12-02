package com.api.dropship.req;

import com.models.aliex.AliexProductFull;
import com.models.aliex.store.AliexStoreInfo;

public class TransformAliexToAmzReq {
    public String diskSerialNumber;
    public AliexProductFull aliexProductFull;
    public AliexStoreInfo aliexStoreInfo;

    public int fetchingImageFromDes;
}
