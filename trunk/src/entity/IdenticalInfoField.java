package entity;

/**
 * 特殊的{@link InfoField}，他们是每个用户所唯一的。也就是说用户A和B不可能拥有一样的IdenticalInfoField，比如他们的邮箱、
 * 身份证号码、手机等都是Identical的。因此，这些特殊的字段可以用来区分用户，并且作为用户登录的凭证。
 * @author Administrator
 *
 */
public interface IdenticalInfoField extends InfoField {

}
