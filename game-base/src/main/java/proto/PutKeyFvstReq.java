// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: fivestone.proto

package proto;

/**
 * <pre>
 * 落子
 * </pre>
 *
 * Protobuf type {@code proto.PutKeyFvstReq}
 */
public  final class PutKeyFvstReq extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:proto.PutKeyFvstReq)
    PutKeyFvstReqOrBuilder {
private static final long serialVersionUID = 0L;
  // Use PutKeyFvstReq.newBuilder() to construct.
  private PutKeyFvstReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private PutKeyFvstReq() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new PutKeyFvstReq();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private PutKeyFvstReq(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            x_ = input.readInt32();
            break;
          }
          case 16: {

            y_ = input.readInt32();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return proto.Fivestone.internal_static_proto_PutKeyFvstReq_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return proto.Fivestone.internal_static_proto_PutKeyFvstReq_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            proto.PutKeyFvstReq.class, proto.PutKeyFvstReq.Builder.class);
  }

  public static final int X_FIELD_NUMBER = 1;
  private int x_;
  /**
   * <code>int32 x = 1;</code>
   * @return The x.
   */
  public int getX() {
    return x_;
  }

  public static final int Y_FIELD_NUMBER = 2;
  private int y_;
  /**
   * <code>int32 y = 2;</code>
   * @return The y.
   */
  public int getY() {
    return y_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (x_ != 0) {
      output.writeInt32(1, x_);
    }
    if (y_ != 0) {
      output.writeInt32(2, y_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (x_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, x_);
    }
    if (y_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, y_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof proto.PutKeyFvstReq)) {
      return super.equals(obj);
    }
    proto.PutKeyFvstReq other = (proto.PutKeyFvstReq) obj;

    if (getX()
        != other.getX()) return false;
    if (getY()
        != other.getY()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + X_FIELD_NUMBER;
    hash = (53 * hash) + getX();
    hash = (37 * hash) + Y_FIELD_NUMBER;
    hash = (53 * hash) + getY();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static proto.PutKeyFvstReq parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.PutKeyFvstReq parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.PutKeyFvstReq parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.PutKeyFvstReq parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.PutKeyFvstReq parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.PutKeyFvstReq parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.PutKeyFvstReq parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static proto.PutKeyFvstReq parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static proto.PutKeyFvstReq parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static proto.PutKeyFvstReq parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static proto.PutKeyFvstReq parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static proto.PutKeyFvstReq parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(proto.PutKeyFvstReq prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * 落子
   * </pre>
   *
   * Protobuf type {@code proto.PutKeyFvstReq}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:proto.PutKeyFvstReq)
      proto.PutKeyFvstReqOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return proto.Fivestone.internal_static_proto_PutKeyFvstReq_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return proto.Fivestone.internal_static_proto_PutKeyFvstReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              proto.PutKeyFvstReq.class, proto.PutKeyFvstReq.Builder.class);
    }

    // Construct using proto.PutKeyFvstReq.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      x_ = 0;

      y_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return proto.Fivestone.internal_static_proto_PutKeyFvstReq_descriptor;
    }

    @java.lang.Override
    public proto.PutKeyFvstReq getDefaultInstanceForType() {
      return proto.PutKeyFvstReq.getDefaultInstance();
    }

    @java.lang.Override
    public proto.PutKeyFvstReq build() {
      proto.PutKeyFvstReq result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public proto.PutKeyFvstReq buildPartial() {
      proto.PutKeyFvstReq result = new proto.PutKeyFvstReq(this);
      result.x_ = x_;
      result.y_ = y_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof proto.PutKeyFvstReq) {
        return mergeFrom((proto.PutKeyFvstReq)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(proto.PutKeyFvstReq other) {
      if (other == proto.PutKeyFvstReq.getDefaultInstance()) return this;
      if (other.getX() != 0) {
        setX(other.getX());
      }
      if (other.getY() != 0) {
        setY(other.getY());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      proto.PutKeyFvstReq parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (proto.PutKeyFvstReq) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int x_ ;
    /**
     * <code>int32 x = 1;</code>
     * @return The x.
     */
    public int getX() {
      return x_;
    }
    /**
     * <code>int32 x = 1;</code>
     * @param value The x to set.
     * @return This builder for chaining.
     */
    public Builder setX(int value) {
      
      x_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 x = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearX() {
      
      x_ = 0;
      onChanged();
      return this;
    }

    private int y_ ;
    /**
     * <code>int32 y = 2;</code>
     * @return The y.
     */
    public int getY() {
      return y_;
    }
    /**
     * <code>int32 y = 2;</code>
     * @param value The y to set.
     * @return This builder for chaining.
     */
    public Builder setY(int value) {
      
      y_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 y = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearY() {
      
      y_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:proto.PutKeyFvstReq)
  }

  // @@protoc_insertion_point(class_scope:proto.PutKeyFvstReq)
  private static final proto.PutKeyFvstReq DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new proto.PutKeyFvstReq();
  }

  public static proto.PutKeyFvstReq getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PutKeyFvstReq>
      PARSER = new com.google.protobuf.AbstractParser<PutKeyFvstReq>() {
    @java.lang.Override
    public PutKeyFvstReq parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new PutKeyFvstReq(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<PutKeyFvstReq> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PutKeyFvstReq> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public proto.PutKeyFvstReq getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

