package com.xyz.tiger.dao.base.annotation;

/**
 * @Description 
 * 		定义公共值的约束类
 * @author Hanht
 * @date 2016年8月10日 上午10:10:31
 */
public interface PUBVALUE {

	/**
	 * @Description 
	 * 		主键生成侧率
	 * @author Hanht
	 * @date 2016年8月10日 上午11:29:08
	 */
	public enum PkRule{
		/**
		 * 生成一个32位UUID字符串作为主键<br>
		 * uuid长度大，占用空间大，跨数据库，不用访问数据库就生成主键值，所以效率高且能保证唯一性，移植非常方便，推荐使用。
		 */
		UUID("UUID"),
		/**
		 * 从数据库中取出主键的最大值，以该值为基础，每次增量为1。<br>
		 * 跨数据库，不适合多进程并发更新数据库，适合单一进程访问数据库，不能用于群集环境。<br>
		 * 目前只实现了针对MySQL数据库的实现。主键加上必须设置auto_increment的属性。
		 */
		INCREMENT("INCREMENT"),
		/**
		 * 采用数据库提供的sequence机制生成主键 <br>
		 * 只能在支持序列的数据库中使用
		 */
		SEQUENCE("SEQUENCE");
		
		private final String value;
		
		PkRule(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
	}
	
}
