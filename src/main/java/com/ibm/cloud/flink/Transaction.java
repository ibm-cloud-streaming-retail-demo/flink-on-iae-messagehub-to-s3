/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.ibm.cloud.flink;  
@SuppressWarnings("all")
/** An invoice line item transaction */
@org.apache.avro.specific.AvroGenerated
public class Transaction extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Transaction\",\"namespace\":\"com.ibm.cloud.flink\",\"doc\":\"An invoice line item transaction\",\"fields\":[{\"name\":\"InvoiceNo\",\"type\":\"int\"},{\"name\":\"StockCode\",\"type\":\"int\"},{\"name\":\"Description\",\"type\":\"string\"},{\"name\":\"Quantity\",\"type\":\"int\"},{\"name\":\"InvoiceDate\",\"type\":\"long\"},{\"name\":\"UnitPrice\",\"type\":\"float\"},{\"name\":\"CustomerID\",\"type\":\"int\"},{\"name\":\"Country\",\"type\":\"string\"},{\"name\":\"LineNo\",\"type\":\"int\"},{\"name\":\"InvoiceTime\",\"type\":\"string\"},{\"name\":\"StoreID\",\"type\":\"int\"},{\"name\":\"TransactionID\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public int InvoiceNo;
  @Deprecated public int StockCode;
  @Deprecated public java.lang.CharSequence Description;
  @Deprecated public int Quantity;
  @Deprecated public long InvoiceDate;
  @Deprecated public float UnitPrice;
  @Deprecated public int CustomerID;
  @Deprecated public java.lang.CharSequence Country;
  @Deprecated public int LineNo;
  @Deprecated public java.lang.CharSequence InvoiceTime;
  @Deprecated public int StoreID;
  @Deprecated public java.lang.CharSequence TransactionID;

  /**
   * Default constructor.
   */
  public Transaction() {}

  /**
   * All-args constructor.
   */
  public Transaction(java.lang.Integer InvoiceNo, java.lang.Integer StockCode, java.lang.CharSequence Description, java.lang.Integer Quantity, java.lang.Long InvoiceDate, java.lang.Float UnitPrice, java.lang.Integer CustomerID, java.lang.CharSequence Country, java.lang.Integer LineNo, java.lang.CharSequence InvoiceTime, java.lang.Integer StoreID, java.lang.CharSequence TransactionID) {
    this.InvoiceNo = InvoiceNo;
    this.StockCode = StockCode;
    this.Description = Description;
    this.Quantity = Quantity;
    this.InvoiceDate = InvoiceDate;
    this.UnitPrice = UnitPrice;
    this.CustomerID = CustomerID;
    this.Country = Country;
    this.LineNo = LineNo;
    this.InvoiceTime = InvoiceTime;
    this.StoreID = StoreID;
    this.TransactionID = TransactionID;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return InvoiceNo;
    case 1: return StockCode;
    case 2: return Description;
    case 3: return Quantity;
    case 4: return InvoiceDate;
    case 5: return UnitPrice;
    case 6: return CustomerID;
    case 7: return Country;
    case 8: return LineNo;
    case 9: return InvoiceTime;
    case 10: return StoreID;
    case 11: return TransactionID;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: InvoiceNo = (java.lang.Integer)value$; break;
    case 1: StockCode = (java.lang.Integer)value$; break;
    case 2: Description = (java.lang.CharSequence)value$; break;
    case 3: Quantity = (java.lang.Integer)value$; break;
    case 4: InvoiceDate = (java.lang.Long)value$; break;
    case 5: UnitPrice = (java.lang.Float)value$; break;
    case 6: CustomerID = (java.lang.Integer)value$; break;
    case 7: Country = (java.lang.CharSequence)value$; break;
    case 8: LineNo = (java.lang.Integer)value$; break;
    case 9: InvoiceTime = (java.lang.CharSequence)value$; break;
    case 10: StoreID = (java.lang.Integer)value$; break;
    case 11: TransactionID = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'InvoiceNo' field.
   */
  public java.lang.Integer getInvoiceNo() {
    return InvoiceNo;
  }

  /**
   * Sets the value of the 'InvoiceNo' field.
   * @param value the value to set.
   */
  public void setInvoiceNo(java.lang.Integer value) {
    this.InvoiceNo = value;
  }

  /**
   * Gets the value of the 'StockCode' field.
   */
  public java.lang.Integer getStockCode() {
    return StockCode;
  }

  /**
   * Sets the value of the 'StockCode' field.
   * @param value the value to set.
   */
  public void setStockCode(java.lang.Integer value) {
    this.StockCode = value;
  }

  /**
   * Gets the value of the 'Description' field.
   */
  public java.lang.CharSequence getDescription() {
    return Description;
  }

  /**
   * Sets the value of the 'Description' field.
   * @param value the value to set.
   */
  public void setDescription(java.lang.CharSequence value) {
    this.Description = value;
  }

  /**
   * Gets the value of the 'Quantity' field.
   */
  public java.lang.Integer getQuantity() {
    return Quantity;
  }

  /**
   * Sets the value of the 'Quantity' field.
   * @param value the value to set.
   */
  public void setQuantity(java.lang.Integer value) {
    this.Quantity = value;
  }

  /**
   * Gets the value of the 'InvoiceDate' field.
   */
  public java.lang.Long getInvoiceDate() {
    return InvoiceDate;
  }

  /**
   * Sets the value of the 'InvoiceDate' field.
   * @param value the value to set.
   */
  public void setInvoiceDate(java.lang.Long value) {
    this.InvoiceDate = value;
  }

  /**
   * Gets the value of the 'UnitPrice' field.
   */
  public java.lang.Float getUnitPrice() {
    return UnitPrice;
  }

  /**
   * Sets the value of the 'UnitPrice' field.
   * @param value the value to set.
   */
  public void setUnitPrice(java.lang.Float value) {
    this.UnitPrice = value;
  }

  /**
   * Gets the value of the 'CustomerID' field.
   */
  public java.lang.Integer getCustomerID() {
    return CustomerID;
  }

  /**
   * Sets the value of the 'CustomerID' field.
   * @param value the value to set.
   */
  public void setCustomerID(java.lang.Integer value) {
    this.CustomerID = value;
  }

  /**
   * Gets the value of the 'Country' field.
   */
  public java.lang.CharSequence getCountry() {
    return Country;
  }

  /**
   * Sets the value of the 'Country' field.
   * @param value the value to set.
   */
  public void setCountry(java.lang.CharSequence value) {
    this.Country = value;
  }

  /**
   * Gets the value of the 'LineNo' field.
   */
  public java.lang.Integer getLineNo() {
    return LineNo;
  }

  /**
   * Sets the value of the 'LineNo' field.
   * @param value the value to set.
   */
  public void setLineNo(java.lang.Integer value) {
    this.LineNo = value;
  }

  /**
   * Gets the value of the 'InvoiceTime' field.
   */
  public java.lang.CharSequence getInvoiceTime() {
    return InvoiceTime;
  }

  /**
   * Sets the value of the 'InvoiceTime' field.
   * @param value the value to set.
   */
  public void setInvoiceTime(java.lang.CharSequence value) {
    this.InvoiceTime = value;
  }

  /**
   * Gets the value of the 'StoreID' field.
   */
  public java.lang.Integer getStoreID() {
    return StoreID;
  }

  /**
   * Sets the value of the 'StoreID' field.
   * @param value the value to set.
   */
  public void setStoreID(java.lang.Integer value) {
    this.StoreID = value;
  }

  /**
   * Gets the value of the 'TransactionID' field.
   */
  public java.lang.CharSequence getTransactionID() {
    return TransactionID;
  }

  /**
   * Sets the value of the 'TransactionID' field.
   * @param value the value to set.
   */
  public void setTransactionID(java.lang.CharSequence value) {
    this.TransactionID = value;
  }

  /** Creates a new Transaction RecordBuilder */
  public static com.ibm.cloud.flink.Transaction.Builder newBuilder() {
    return new com.ibm.cloud.flink.Transaction.Builder();
  }
  
  /** Creates a new Transaction RecordBuilder by copying an existing Builder */
  public static com.ibm.cloud.flink.Transaction.Builder newBuilder(com.ibm.cloud.flink.Transaction.Builder other) {
    return new com.ibm.cloud.flink.Transaction.Builder(other);
  }
  
  /** Creates a new Transaction RecordBuilder by copying an existing Transaction instance */
  public static com.ibm.cloud.flink.Transaction.Builder newBuilder(com.ibm.cloud.flink.Transaction other) {
    return new com.ibm.cloud.flink.Transaction.Builder(other);
  }
  
  /**
   * RecordBuilder for Transaction instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Transaction>
    implements org.apache.avro.data.RecordBuilder<Transaction> {

    private int InvoiceNo;
    private int StockCode;
    private java.lang.CharSequence Description;
    private int Quantity;
    private long InvoiceDate;
    private float UnitPrice;
    private int CustomerID;
    private java.lang.CharSequence Country;
    private int LineNo;
    private java.lang.CharSequence InvoiceTime;
    private int StoreID;
    private java.lang.CharSequence TransactionID;

    /** Creates a new Builder */
    private Builder() {
      super(com.ibm.cloud.flink.Transaction.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.ibm.cloud.flink.Transaction.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing Transaction instance */
    private Builder(com.ibm.cloud.flink.Transaction other) {
            super(com.ibm.cloud.flink.Transaction.SCHEMA$);
      if (isValidValue(fields()[0], other.InvoiceNo)) {
        this.InvoiceNo = data().deepCopy(fields()[0].schema(), other.InvoiceNo);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.StockCode)) {
        this.StockCode = data().deepCopy(fields()[1].schema(), other.StockCode);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.Description)) {
        this.Description = data().deepCopy(fields()[2].schema(), other.Description);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.Quantity)) {
        this.Quantity = data().deepCopy(fields()[3].schema(), other.Quantity);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.InvoiceDate)) {
        this.InvoiceDate = data().deepCopy(fields()[4].schema(), other.InvoiceDate);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.UnitPrice)) {
        this.UnitPrice = data().deepCopy(fields()[5].schema(), other.UnitPrice);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.CustomerID)) {
        this.CustomerID = data().deepCopy(fields()[6].schema(), other.CustomerID);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.Country)) {
        this.Country = data().deepCopy(fields()[7].schema(), other.Country);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.LineNo)) {
        this.LineNo = data().deepCopy(fields()[8].schema(), other.LineNo);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.InvoiceTime)) {
        this.InvoiceTime = data().deepCopy(fields()[9].schema(), other.InvoiceTime);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.StoreID)) {
        this.StoreID = data().deepCopy(fields()[10].schema(), other.StoreID);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.TransactionID)) {
        this.TransactionID = data().deepCopy(fields()[11].schema(), other.TransactionID);
        fieldSetFlags()[11] = true;
      }
    }

    /** Gets the value of the 'InvoiceNo' field */
    public java.lang.Integer getInvoiceNo() {
      return InvoiceNo;
    }
    
    /** Sets the value of the 'InvoiceNo' field */
    public com.ibm.cloud.flink.Transaction.Builder setInvoiceNo(int value) {
      validate(fields()[0], value);
      this.InvoiceNo = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'InvoiceNo' field has been set */
    public boolean hasInvoiceNo() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'InvoiceNo' field */
    public com.ibm.cloud.flink.Transaction.Builder clearInvoiceNo() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'StockCode' field */
    public java.lang.Integer getStockCode() {
      return StockCode;
    }
    
    /** Sets the value of the 'StockCode' field */
    public com.ibm.cloud.flink.Transaction.Builder setStockCode(int value) {
      validate(fields()[1], value);
      this.StockCode = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'StockCode' field has been set */
    public boolean hasStockCode() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'StockCode' field */
    public com.ibm.cloud.flink.Transaction.Builder clearStockCode() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'Description' field */
    public java.lang.CharSequence getDescription() {
      return Description;
    }
    
    /** Sets the value of the 'Description' field */
    public com.ibm.cloud.flink.Transaction.Builder setDescription(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.Description = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'Description' field has been set */
    public boolean hasDescription() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'Description' field */
    public com.ibm.cloud.flink.Transaction.Builder clearDescription() {
      Description = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'Quantity' field */
    public java.lang.Integer getQuantity() {
      return Quantity;
    }
    
    /** Sets the value of the 'Quantity' field */
    public com.ibm.cloud.flink.Transaction.Builder setQuantity(int value) {
      validate(fields()[3], value);
      this.Quantity = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'Quantity' field has been set */
    public boolean hasQuantity() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'Quantity' field */
    public com.ibm.cloud.flink.Transaction.Builder clearQuantity() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'InvoiceDate' field */
    public java.lang.Long getInvoiceDate() {
      return InvoiceDate;
    }
    
    /** Sets the value of the 'InvoiceDate' field */
    public com.ibm.cloud.flink.Transaction.Builder setInvoiceDate(long value) {
      validate(fields()[4], value);
      this.InvoiceDate = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'InvoiceDate' field has been set */
    public boolean hasInvoiceDate() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'InvoiceDate' field */
    public com.ibm.cloud.flink.Transaction.Builder clearInvoiceDate() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'UnitPrice' field */
    public java.lang.Float getUnitPrice() {
      return UnitPrice;
    }
    
    /** Sets the value of the 'UnitPrice' field */
    public com.ibm.cloud.flink.Transaction.Builder setUnitPrice(float value) {
      validate(fields()[5], value);
      this.UnitPrice = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'UnitPrice' field has been set */
    public boolean hasUnitPrice() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'UnitPrice' field */
    public com.ibm.cloud.flink.Transaction.Builder clearUnitPrice() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'CustomerID' field */
    public java.lang.Integer getCustomerID() {
      return CustomerID;
    }
    
    /** Sets the value of the 'CustomerID' field */
    public com.ibm.cloud.flink.Transaction.Builder setCustomerID(int value) {
      validate(fields()[6], value);
      this.CustomerID = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'CustomerID' field has been set */
    public boolean hasCustomerID() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'CustomerID' field */
    public com.ibm.cloud.flink.Transaction.Builder clearCustomerID() {
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'Country' field */
    public java.lang.CharSequence getCountry() {
      return Country;
    }
    
    /** Sets the value of the 'Country' field */
    public com.ibm.cloud.flink.Transaction.Builder setCountry(java.lang.CharSequence value) {
      validate(fields()[7], value);
      this.Country = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'Country' field has been set */
    public boolean hasCountry() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'Country' field */
    public com.ibm.cloud.flink.Transaction.Builder clearCountry() {
      Country = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /** Gets the value of the 'LineNo' field */
    public java.lang.Integer getLineNo() {
      return LineNo;
    }
    
    /** Sets the value of the 'LineNo' field */
    public com.ibm.cloud.flink.Transaction.Builder setLineNo(int value) {
      validate(fields()[8], value);
      this.LineNo = value;
      fieldSetFlags()[8] = true;
      return this; 
    }
    
    /** Checks whether the 'LineNo' field has been set */
    public boolean hasLineNo() {
      return fieldSetFlags()[8];
    }
    
    /** Clears the value of the 'LineNo' field */
    public com.ibm.cloud.flink.Transaction.Builder clearLineNo() {
      fieldSetFlags()[8] = false;
      return this;
    }

    /** Gets the value of the 'InvoiceTime' field */
    public java.lang.CharSequence getInvoiceTime() {
      return InvoiceTime;
    }
    
    /** Sets the value of the 'InvoiceTime' field */
    public com.ibm.cloud.flink.Transaction.Builder setInvoiceTime(java.lang.CharSequence value) {
      validate(fields()[9], value);
      this.InvoiceTime = value;
      fieldSetFlags()[9] = true;
      return this; 
    }
    
    /** Checks whether the 'InvoiceTime' field has been set */
    public boolean hasInvoiceTime() {
      return fieldSetFlags()[9];
    }
    
    /** Clears the value of the 'InvoiceTime' field */
    public com.ibm.cloud.flink.Transaction.Builder clearInvoiceTime() {
      InvoiceTime = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /** Gets the value of the 'StoreID' field */
    public java.lang.Integer getStoreID() {
      return StoreID;
    }
    
    /** Sets the value of the 'StoreID' field */
    public com.ibm.cloud.flink.Transaction.Builder setStoreID(int value) {
      validate(fields()[10], value);
      this.StoreID = value;
      fieldSetFlags()[10] = true;
      return this; 
    }
    
    /** Checks whether the 'StoreID' field has been set */
    public boolean hasStoreID() {
      return fieldSetFlags()[10];
    }
    
    /** Clears the value of the 'StoreID' field */
    public com.ibm.cloud.flink.Transaction.Builder clearStoreID() {
      fieldSetFlags()[10] = false;
      return this;
    }

    /** Gets the value of the 'TransactionID' field */
    public java.lang.CharSequence getTransactionID() {
      return TransactionID;
    }
    
    /** Sets the value of the 'TransactionID' field */
    public com.ibm.cloud.flink.Transaction.Builder setTransactionID(java.lang.CharSequence value) {
      validate(fields()[11], value);
      this.TransactionID = value;
      fieldSetFlags()[11] = true;
      return this; 
    }
    
    /** Checks whether the 'TransactionID' field has been set */
    public boolean hasTransactionID() {
      return fieldSetFlags()[11];
    }
    
    /** Clears the value of the 'TransactionID' field */
    public com.ibm.cloud.flink.Transaction.Builder clearTransactionID() {
      TransactionID = null;
      fieldSetFlags()[11] = false;
      return this;
    }

    @Override
    public Transaction build() {
      try {
        Transaction record = new Transaction();
        record.InvoiceNo = fieldSetFlags()[0] ? this.InvoiceNo : (java.lang.Integer) defaultValue(fields()[0]);
        record.StockCode = fieldSetFlags()[1] ? this.StockCode : (java.lang.Integer) defaultValue(fields()[1]);
        record.Description = fieldSetFlags()[2] ? this.Description : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.Quantity = fieldSetFlags()[3] ? this.Quantity : (java.lang.Integer) defaultValue(fields()[3]);
        record.InvoiceDate = fieldSetFlags()[4] ? this.InvoiceDate : (java.lang.Long) defaultValue(fields()[4]);
        record.UnitPrice = fieldSetFlags()[5] ? this.UnitPrice : (java.lang.Float) defaultValue(fields()[5]);
        record.CustomerID = fieldSetFlags()[6] ? this.CustomerID : (java.lang.Integer) defaultValue(fields()[6]);
        record.Country = fieldSetFlags()[7] ? this.Country : (java.lang.CharSequence) defaultValue(fields()[7]);
        record.LineNo = fieldSetFlags()[8] ? this.LineNo : (java.lang.Integer) defaultValue(fields()[8]);
        record.InvoiceTime = fieldSetFlags()[9] ? this.InvoiceTime : (java.lang.CharSequence) defaultValue(fields()[9]);
        record.StoreID = fieldSetFlags()[10] ? this.StoreID : (java.lang.Integer) defaultValue(fields()[10]);
        record.TransactionID = fieldSetFlags()[11] ? this.TransactionID : (java.lang.CharSequence) defaultValue(fields()[11]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
