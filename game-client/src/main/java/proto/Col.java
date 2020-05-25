// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Wuziqi.proto

package proto;

/**
 * Protobuf type {@code Message.Col}
 */
public  final class Col extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:Message.Col)
    ColOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Col.newBuilder() to construct.
  private Col(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Col() {
    side_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Col();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Col(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
            int rawValue = input.readEnum();
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              side_ = new java.util.ArrayList<java.lang.Integer>();
              mutable_bitField0_ |= 0x00000001;
            }
            side_.add(rawValue);
            break;
          }
          case 10: {
            int length = input.readRawVarint32();
            int oldLimit = input.pushLimit(length);
            while(input.getBytesUntilLimit() > 0) {
              int rawValue = input.readEnum();
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                side_ = new java.util.ArrayList<java.lang.Integer>();
                mutable_bitField0_ |= 0x00000001;
              }
              side_.add(rawValue);
            }
            input.popLimit(oldLimit);
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        side_ = java.util.Collections.unmodifiableList(side_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return proto.Gobang.internal_static_Message_Col_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return proto.Gobang.internal_static_Message_Col_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            proto.Col.class, proto.Col.Builder.class);
  }

  public static final int SIDE_FIELD_NUMBER = 1;
  private java.util.List<java.lang.Integer> side_;
  private static final com.google.protobuf.Internal.ListAdapter.Converter<
      java.lang.Integer, proto.Side> side_converter_ =
          new com.google.protobuf.Internal.ListAdapter.Converter<
              java.lang.Integer, proto.Side>() {
            public proto.Side convert(java.lang.Integer from) {
              @SuppressWarnings("deprecation")
              proto.Side result = proto.Side.valueOf(from);
              return result == null ? proto.Side.UNRECOGNIZED : result;
            }
          };
  /**
   * <code>repeated .Message.Side side = 1;</code>
   * @return A list containing the side.
   */
  public java.util.List<proto.Side> getSideList() {
    return new com.google.protobuf.Internal.ListAdapter<
        java.lang.Integer, proto.Side>(side_, side_converter_);
  }
  /**
   * <code>repeated .Message.Side side = 1;</code>
   * @return The count of side.
   */
  public int getSideCount() {
    return side_.size();
  }
  /**
   * <code>repeated .Message.Side side = 1;</code>
   * @param index The index of the element to return.
   * @return The side at the given index.
   */
  public proto.Side getSide(int index) {
    return side_converter_.convert(side_.get(index));
  }
  /**
   * <code>repeated .Message.Side side = 1;</code>
   * @return A list containing the enum numeric values on the wire for side.
   */
  public java.util.List<java.lang.Integer>
  getSideValueList() {
    return side_;
  }
  /**
   * <code>repeated .Message.Side side = 1;</code>
   * @param index The index of the value to return.
   * @return The enum numeric value on the wire of side at the given index.
   */
  public int getSideValue(int index) {
    return side_.get(index);
  }
  private int sideMemoizedSerializedSize;

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
    getSerializedSize();
    if (getSideList().size() > 0) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(sideMemoizedSerializedSize);
    }
    for (int i = 0; i < side_.size(); i++) {
      output.writeEnumNoTag(side_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      for (int i = 0; i < side_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeEnumSizeNoTag(side_.get(i));
      }
      size += dataSize;
      if (!getSideList().isEmpty()) {  size += 1;
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32SizeNoTag(dataSize);
      }sideMemoizedSerializedSize = dataSize;
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
    if (!(obj instanceof proto.Col)) {
      return super.equals(obj);
    }
    proto.Col other = (proto.Col) obj;

    if (!side_.equals(other.side_)) return false;
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
    if (getSideCount() > 0) {
      hash = (37 * hash) + SIDE_FIELD_NUMBER;
      hash = (53 * hash) + side_.hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static proto.Col parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.Col parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.Col parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.Col parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.Col parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.Col parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.Col parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static proto.Col parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static proto.Col parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static proto.Col parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static proto.Col parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static proto.Col parseFrom(
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
  public static Builder newBuilder(proto.Col prototype) {
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
   * Protobuf type {@code Message.Col}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:Message.Col)
      proto.ColOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return proto.Gobang.internal_static_Message_Col_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return proto.Gobang.internal_static_Message_Col_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              proto.Col.class, proto.Col.Builder.class);
    }

    // Construct using proto.Col.newBuilder()
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
      side_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return proto.Gobang.internal_static_Message_Col_descriptor;
    }

    @java.lang.Override
    public proto.Col getDefaultInstanceForType() {
      return proto.Col.getDefaultInstance();
    }

    @java.lang.Override
    public proto.Col build() {
      proto.Col result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public proto.Col buildPartial() {
      proto.Col result = new proto.Col(this);
      int from_bitField0_ = bitField0_;
      if (((bitField0_ & 0x00000001) != 0)) {
        side_ = java.util.Collections.unmodifiableList(side_);
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.side_ = side_;
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
      if (other instanceof proto.Col) {
        return mergeFrom((proto.Col)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(proto.Col other) {
      if (other == proto.Col.getDefaultInstance()) return this;
      if (!other.side_.isEmpty()) {
        if (side_.isEmpty()) {
          side_ = other.side_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureSideIsMutable();
          side_.addAll(other.side_);
        }
        onChanged();
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
      proto.Col parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (proto.Col) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<java.lang.Integer> side_ =
      java.util.Collections.emptyList();
    private void ensureSideIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        side_ = new java.util.ArrayList<java.lang.Integer>(side_);
        bitField0_ |= 0x00000001;
      }
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @return A list containing the side.
     */
    public java.util.List<proto.Side> getSideList() {
      return new com.google.protobuf.Internal.ListAdapter<
          java.lang.Integer, proto.Side>(side_, side_converter_);
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @return The count of side.
     */
    public int getSideCount() {
      return side_.size();
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @param index The index of the element to return.
     * @return The side at the given index.
     */
    public proto.Side getSide(int index) {
      return side_converter_.convert(side_.get(index));
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @param index The index to set the value at.
     * @param value The side to set.
     * @return This builder for chaining.
     */
    public Builder setSide(
        int index, proto.Side value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureSideIsMutable();
      side_.set(index, value.getNumber());
      onChanged();
      return this;
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @param value The side to add.
     * @return This builder for chaining.
     */
    public Builder addSide(proto.Side value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureSideIsMutable();
      side_.add(value.getNumber());
      onChanged();
      return this;
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @param values The side to add.
     * @return This builder for chaining.
     */
    public Builder addAllSide(
        java.lang.Iterable<? extends proto.Side> values) {
      ensureSideIsMutable();
      for (proto.Side value : values) {
        side_.add(value.getNumber());
      }
      onChanged();
      return this;
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearSide() {
      side_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @return A list containing the enum numeric values on the wire for side.
     */
    public java.util.List<java.lang.Integer>
    getSideValueList() {
      return java.util.Collections.unmodifiableList(side_);
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @param index The index of the value to return.
     * @return The enum numeric value on the wire of side at the given index.
     */
    public int getSideValue(int index) {
      return side_.get(index);
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @param index The index of the value to return.
     * @return The enum numeric value on the wire of side at the given index.
     * @return This builder for chaining.
     */
    public Builder setSideValue(
        int index, int value) {
      ensureSideIsMutable();
      side_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @param value The enum numeric value on the wire for side to add.
     * @return This builder for chaining.
     */
    public Builder addSideValue(int value) {
      ensureSideIsMutable();
      side_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated .Message.Side side = 1;</code>
     * @param values The enum numeric values on the wire for side to add.
     * @return This builder for chaining.
     */
    public Builder addAllSideValue(
        java.lang.Iterable<java.lang.Integer> values) {
      ensureSideIsMutable();
      for (int value : values) {
        side_.add(value);
      }
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


    // @@protoc_insertion_point(builder_scope:Message.Col)
  }

  // @@protoc_insertion_point(class_scope:Message.Col)
  private static final proto.Col DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new proto.Col();
  }

  public static proto.Col getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Col>
      PARSER = new com.google.protobuf.AbstractParser<Col>() {
    @java.lang.Override
    public Col parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Col(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Col> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Col> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public proto.Col getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
