// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: fightgame.proto

package proto;

public interface BattleFightReqOrBuilder extends
    // @@protoc_insertion_point(interface_extends:proto.BattleFightReq)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 玩家阵容
   * </pre>
   *
   * <code>repeated .proto.BattlePos BattlePos = 1;</code>
   */
  java.util.List<proto.BattlePos> 
      getBattlePosList();
  /**
   * <pre>
   * 玩家阵容
   * </pre>
   *
   * <code>repeated .proto.BattlePos BattlePos = 1;</code>
   */
  proto.BattlePos getBattlePos(int index);
  /**
   * <pre>
   * 玩家阵容
   * </pre>
   *
   * <code>repeated .proto.BattlePos BattlePos = 1;</code>
   */
  int getBattlePosCount();
  /**
   * <pre>
   * 玩家阵容
   * </pre>
   *
   * <code>repeated .proto.BattlePos BattlePos = 1;</code>
   */
  java.util.List<? extends proto.BattlePosOrBuilder> 
      getBattlePosOrBuilderList();
  /**
   * <pre>
   * 玩家阵容
   * </pre>
   *
   * <code>repeated .proto.BattlePos BattlePos = 1;</code>
   */
  proto.BattlePosOrBuilder getBattlePosOrBuilder(
      int index);

  /**
   * <pre>
   * 敌方阵容Id
   * </pre>
   *
   * <code>int64 battleId = 2;</code>
   * @return The battleId.
   */
  long getBattleId();
}
