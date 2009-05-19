package entity.infoField;

/**
 * 特殊的{@link InfoField}，表示该字段在数据库或者数据结构中建立了索引，
 * 可以在搜索时作为关键字来快速索引，而不用线性的一个一个扫描比较。
 * @author Administrator
 *
 */
public interface IndexedInfoField extends InfoField {

}
