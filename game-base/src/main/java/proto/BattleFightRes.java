// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: fightgame.proto

package proto;

/**
 * Protobuf type {@code proto.BattleFightRes}
 */
public  final class BattleFightRes extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:proto.BattleFightRes)
    BattleFightResOrBuilder {
private static final long serialVersionUID = 0L;
  // Use BattleFightRes.newBuilder() to construct.
  private BattleFightRes(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private BattleFightRes() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new BattleFightRes();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private BattleFightRes(
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

            result_ = input.readBool();
            break;
          }
          case 16: {

            star_ = input.readInt32();
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
    return proto.Fightgame.internal_static_proto_BattleFightRes_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return proto.Fightgame.internal_static_proto_BattleFightRes_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            proto.BattleFightRes.class, proto.BattleFightRes.Builder.class);
  }

  public static final int RESULT_FIELD_NUMBER = 1;
  private boolean result_;
  /**
   * <pre>
   * true成功，false失败
   * </pre>
   *
   * <code>bool result = 1;</code>
   * @return The result.
   */
  public boolean getResult() {
    return result_;
  }

  public static final int STAR_FIELD_NUMBER = 2;
  private int star_;
  /**
   * <pre>
   * 星数
   * </pre>
   *
   * <code>int32 star = 2;</code>
   * @return The star.
   */
  public int getStar() {
    return star_;
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
    if (result_ != false) {
      output.writeBool(1, result_);
    }
    if (star_ != 0) {
      output.writeInt32(2, star_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (result_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(1, result_);
    }
    if (star_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, star_);
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
    if (!(obj instanceof proto.BattleFightRes)) {
      return super.equals(obj);
    }
    proto.BattleFightRes other = (proto.BattleFightRes) obj;

    if (getResult()
        != other.getResult()) return false;
    if (getStar()
        != other.getStar()) return false;
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
    hash = (37 * hash) + RESULT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getResult());
    hash = (37 * hash) + STAR_FIELD_NUMBER;
    hash = (53 * hash) + getStar();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static proto.BattleFightRes parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.BattleFightRes parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.BattleFightRes parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.BattleFightRes parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.BattleFightRes parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.BattleFightRes parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.BattleFightRes parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static proto.BattleFightRes parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static proto.BattleFightRes parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static proto.BattleFightRes parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static proto.BattleFightRes parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static proto.BattleFightRes parseFrom(
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
  public static Builder newBuilder(proto.BattleFightRes prototype) {
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
   * Protobuf type {@code proto.BattleFightRes}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:proto.BattleFightRes)
      proto.BattleFightResOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return proto.Fightgame.internal_static_proto_BattleFightRes_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return proto.Fightgame.internal_static_proto_BattleFightRes_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              proto.BattleFightRes.class, proto.BattleFightRes.Builder.class);
    }

    // Construct using proto.BattleFightRes.newBuilder()
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
      result_ = false;

      star_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return proto.Fightgame.internal_static_proto_BattleFightRes_descriptor;
    }

    @java.lang.Override
    public proto.BattleFightRes getDefaultInstanceForType() {
      return proto.BattleFightRes.getDefaultInstance();
    }

    @java.lang.Override
    public proto.BattleFightRes build() {
      proto.BattleFightRes result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public proto.BattleFightRes buildPartial() {
      proto.BattleFightRes result = new proto.BattleFightRes(this);
      result.result_ = result_;
      result.star_ = star_;
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
      if (other instanceof proto.BattleFightRes) {
        return mergeFrom((proto.BattleFightRes)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(proto.BattleFightRes other) {
      if (other == proto.BattleFightRes.getDefaultInstance()) return this;
      if (other.getResult() != false) {
        setResult(other.getResult());
      }
      if (other.getStar() != 0) {
        setStar(other.getStar());
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
      proto.BattleFightRes parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (proto.BattleFightRes) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private boolean result_ ;
    /**
     * <pre>
     * true成功，false失败
     * </pre>
     *
     * <code>bool result = 1;</code>
     * @return The result.
     */
    public boolean getResult() {
      return result_;
    }
    /**
     * <pre>
     * true成功，false失败
     * </pre>
     *
     * <code>bool result = 1;</code>
     * @param value The result to set.
     * @return This builder for chaining.
     */
    public Builder setResult(boolean value) {
      
      result_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * true成功，false失败
     * </pre>
     *
     * <code>bool result = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearResult() {
      
      result_ = false;
      onChanged();
      return this;
    }

    private int star_ ;
    /**
     * <pre>
     * 星数
     * </pre>
     *
     * <code>int32 star = 2;</code>
     * @return The star.
     */
    public int getStar() {
      return star_;
    }
    /**
     * <pre>
     * 星数
     * </pre>
     *
     * <code>int32 star = 2;</code>
     * @param value The star to set.
     * @return This builder for chaining.
     */
    public Builder setStar(int value) {
      
      star_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 星数
     * </pre>
     *
     * <code>int32 star = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearStar() {
      
      star_ = 0;
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


    // @@protoc_insertion_point(builder_scope:proto.BattleFightRes)
  }

  // @@protoc_insertion_point(class_scope:proto.BattleFightRes)
  private static final proto.BattleFightRes DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new proto.BattleFightRes();
  }

  public static proto.BattleFightRes getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<BattleFightRes>
      PARSER = new com.google.protobuf.AbstractParser<BattleFightRes>() {
    @java.lang.Override
    public BattleFightRes parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new BattleFightRes(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<BattleFightRes> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<BattleFightRes> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public proto.BattleFightRes getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

