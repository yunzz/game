// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

package proto;

public final class MessageOuterClass {
  private MessageOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Message_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_proto_Message_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rmessage.proto\022\005proto\032\014common.proto\032\017fi" +
      "ghtgame.proto\032\017fivestone.proto\"\367\010\n\007Messa" +
      "ge\022\013\n\003seq\030\001 \001(\003\022\013\n\003uid\030\002 \001(\006\022\024\n\014broadcas" +
      "tUid\030\003 \003(\006\022\035\n\005hbReq\030\t \001(\0132\014.proto.HbReqH" +
      "\000\022\035\n\005hbRes\030\n \001(\0132\014.proto.HbResH\000\022$\n\nsucc" +
      "essRes\030\013 \001(\0132\016.proto.SuccessH\000\022 \n\010errorR" +
      "es\030\014 \001(\0132\014.proto.ErrorH\000\022#\n\010loginReq\030\r \001" +
      "(\0132\017.proto.LoginReqH\000\022#\n\010loginRes\030\016 \001(\0132" +
      "\017.proto.LoginResH\000\022%\n\tserverReq\030\017 \001(\0132\020." +
      "proto.ServerReqH\000\022!\n\007moveTel\030e \001(\0132\016.pro" +
      "to.MoveTelH\000\022!\n\007moveMsg\030f \001(\0132\016.proto.Mo" +
      "veMsgH\000\022+\n\014EnterRoomReq\030g \001(\0132\023.proto.En" +
      "terRoomReqH\000\022,\n\014CrateRoomReq\030h \001(\0132\024.pro" +
      "to.CreateRoomReqH\000\022,\n\014CrateRoomRes\030i \001(\013" +
      "2\024.proto.CreateRoomResH\000\022)\n\013JoinRoomReq\030" +
      "j \001(\0132\022.proto.JoinRoomReqH\000\022)\n\013FindRoomR" +
      "eq\030k \001(\0132\022.proto.FindRoomReqH\000\022)\n\013FindRo" +
      "omRes\030l \001(\0132\022.proto.FindRoomResH\000\022)\n\013Exi" +
      "tRoomReq\030m \001(\0132\022.proto.ExitRoomReqH\000\022+\n\014" +
      "StartGameReq\030n \001(\0132\023.proto.StartGameReqH" +
      "\000\0224\n\020EnterRoomFvstReq\030\221N \001(\0132\027.proto.Ent" +
      "erRoomFvstReqH\000\0226\n\021CreateRoomFvstReq\030\222N " +
      "\001(\0132\030.proto.CreateRoomFvstReqH\000\022.\n\rPutKe" +
      "yFvstReq\030\223N \001(\0132\024.proto.PutKeyFvstReqH\000\022" +
      ".\n\rPutKeyFvstRes\030\224N \001(\0132\024.proto.PutKeyFv" +
      "stResH\000\022,\n\014StartFvstMsg\030\225N \001(\0132\023.proto.S" +
      "tartFvstMsgH\000\022.\n\rPutKeyFvstMsg\030\226N \001(\0132\024." +
      "proto.PutKeyFvstMsgH\000\0220\n\016GameEndFvstMsg\030" +
      "\227N \001(\0132\025.proto.GameEndFvstMsgH\000\0224\n\020Enter" +
      "RoomFvstRes\030\230N \001(\0132\027.proto.EnterRoomFvst" +
      "ResH\000B\t\n\007contentB\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          proto.Common.getDescriptor(),
          proto.Fightgame.getDescriptor(),
          proto.Fivestone.getDescriptor(),
        });
    internal_static_proto_Message_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_proto_Message_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_proto_Message_descriptor,
        new java.lang.String[] { "Seq", "Uid", "BroadcastUid", "HbReq", "HbRes", "SuccessRes", "ErrorRes", "LoginReq", "LoginRes", "ServerReq", "MoveTel", "MoveMsg", "EnterRoomReq", "CrateRoomReq", "CrateRoomRes", "JoinRoomReq", "FindRoomReq", "FindRoomRes", "ExitRoomReq", "StartGameReq", "EnterRoomFvstReq", "CreateRoomFvstReq", "PutKeyFvstReq", "PutKeyFvstRes", "StartFvstMsg", "PutKeyFvstMsg", "GameEndFvstMsg", "EnterRoomFvstRes", "Content", });
    proto.Common.getDescriptor();
    proto.Fightgame.getDescriptor();
    proto.Fivestone.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
