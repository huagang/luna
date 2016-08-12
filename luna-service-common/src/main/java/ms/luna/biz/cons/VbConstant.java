package ms.luna.biz.cons;

import java.util.*;

public final class VbConstant {
	/**
	 * 缺省的检索件数上限
	 */
	public final static int DEFAULT_MAX_LIMT = 1000;

	
	/**
	 * 游客不能对任何区域操作
	 */
	public static final String REGION_ID_NONE = "NONE";
	public static final int AUTH_GAO_JI_GUAN_LI_YUAN = 999;
	
//	public static final String REGION_NM_GAO_JI_GUAN_LI_YUAN = "全业务";
	
//	public static final String CATEGORY_NM_GAO_JI_GUAN_LI_YUAN = "全部";
	
	public static final String REGION_NM_YOU_KE = "-";
	
	public static final class ZonePulldown {
		public static final String ALL_COUNTY_NM = "请选择区/县";
		public static final String ALL_CITY_NM = "请选择市";
		public static final String ALL_PROVINCE_NM = "请选择省";
		public static final String ALL_COUNTRY_NM = "中国";
		public static final String ALL_CATEGORY_NM = "全部";
		public static final String ALL_REGION_NM = "全业务";
		
		/**
		 * 高级管理员可以针对全部的区域操作
		 */
		public static final String ALL = "ALL";
	}
	

	public static final int AUTH_YOU_KE = 0;

	public static final class LAYOUT {
		public static final Integer 页面布局 = 1;
		public static final Integer 容器布局 = 2;
	}

	/**
	 * Access-Control-Allow-Origin
	 */
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN_KEY = "Access-Control-Allow-Origin";
	public static final String NORMAL_CONTENT_TYPE = "text/html; charset=UTF-8";
	/**
	 * 目前值为“*”
	 */
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN_VALUE = "*";
	/**
	 * 业务模块
	 * @author Mark
	 *
	 */
	public static final class Module {
		public static final class Luna {
			public static final String 皓月平台高级管理员_Code = "luna_senior_admin";
			public static final String 皓月平台管理员_Code = "luna_admin";
			public static final String 皓月平台运营员_Code = "luna_operator";
		}
		
		public static final class Merchant {
			public static final String 商家管理员_Code = "merchant_admin";
			public static final String 商家运营员_Code = "merchant_operator";
		}
		public static final class Poi {
			public static final String 信息数据管理员_Code = "poi_admin";
			public static final String 信息数据运营员_Code = "poi_operator";
		}
		public static final class Vbpano {
			public static final String VBPano管理员_Code = "vbpano_admin";
			public static final String VBPano运营员_Code = "vbpano_operator";
		}
		public static final class Activity {
			public static final String 活动管理员_Code = "activity_admin";
			public static final String 活动运营员_Code = "activity_operator";
		}
	}
	
	public static final class DUMMY_CODE {
		public static final String REGION_ID = "ZZZZZZ";
	}
	
	public static final class DEL_FLG {
		public static final String 删除 = "1";
		public static final String 未删除 = "0";
		public static final String NORMAL = "0";
		public static final String DELETED = "1";
	}
	
	public static final class USER_LOGIN_MODE {
		public static final Integer 用户名密码 = 0;
		public static final Integer 微信登录 = 1;
		public static final Integer QQ登录 = 2;
	}
	public static final class POI_FIELD_TYPE {
		public static final int 文本框 = 1;
		public static final int 复选框列表 = 2;
		public static final int POINT = 3;
		public static final int 文本域 = 4;
		public static final int 图片 = 5;
		public static final int 音频 = 6;
		public static final int 视频 = 7;
		public static final int 下拉列表 = 8;
		public static final int 单选框列表=9;
	}
	public static final class USER_STATUS {
		public static final String 正常 = "0";
		public static final String 锁定 = "1";
	}
	public static final class STATUS {
		public static final class NAME {
			public static final String 名称_未做成 = "未做成";
			public static final String 名称_审核中 = "审核中";
			public static final String 名称_审核通过 = "审核通过";
			public static final String 名称_审核未通过 = "审核未通过";
			public static final String 名称_未发布 = "未发布";
			public static final String 名称_已发布 = "已发布";
			public static final String 名称_已下线 = "已下线";
		}
		public static final class CODE {
			public static final byte 未做成 = 0;
			public static final byte 审核中 = 1;
			public static final byte 审核通过 = 2;
			public static final byte 审核未通过 = 3;
			public static final byte 未发布 = 4;
			public static final byte 已发布 = 5;
			public static final byte 已下线 = 6;
		}

		public static final String ConvertStauts(Byte status) {
			if (CODE.未做成 == status) {
				return NAME.名称_未做成;
			} else if (CODE.审核中 == status) {
				return NAME.名称_审核中;
			} else if (CODE.审核未通过 == status) {
				return NAME.名称_审核未通过;
			}else if (CODE.审核通过 == status) {
				return NAME.名称_审核通过;
			}else if (CODE.已发布 == status) {
				return NAME.名称_已发布;
			}else if (CODE.未发布 == status) {
				return NAME.名称_未发布;
			}else if (CODE.已下线 == status) {
				return NAME.名称_已下线;
			}else {
				return "";
			}
		}
	}
	
	public static final class MERCHANT_STATUS{
		public static final class NAME{
			public static final String 名称_待处理 = "待处理";
			public static final String 名称_信息核实 = "信息核实";
			public static final String 名称_产品制作中 = "产品制作中";
			public static final String 名称_产品交付 = "产品交付";
			public static final String 名称_产品追踪 = "产品追踪";
			public static final String 名称_无效商户 = "无效商户";
		}
		
		public static final class CODE{
			public static final byte 待处理 = 0;
			public static final byte 信息核实 = 1;
			public static final byte 产品制作中 = 2;
			public static final byte 产品交付 = 3;
			public static final byte 产品追踪 = 4;
			public static final byte 无效商户 = 5;
		}
		
		public static final String ConvertStauts(Byte status) {
			if (CODE.待处理 == status) {
				return NAME.名称_待处理;
			} else if (CODE.信息核实 == status) {
				return NAME.名称_信息核实;
			} else if (CODE.产品制作中 == status) {
				return NAME.名称_产品制作中;
			}else if (CODE.产品交付 == status) {
				return NAME.名称_产品交付;
			}else if (CODE.产品追踪 == status) {
				return NAME.名称_产品追踪;
			}else if (CODE.无效商户 == status) {
				return NAME.名称_无效商户;
			}else {
				return "";
			}
		}
	}
	
	public static class MsShowAppConfig {
		
		public static class AppStatus {
			public static final byte DELETE = -1;
			public static final byte NOT_AUDIT = 0;
			public static final byte ONLINE = 1;
			public static final byte OFFLINE = 2;
			
			public static final Map<Byte, String> status2Name = new TreeMap<>();
			
			static {
				status2Name.put(DELETE, "已删除");
				status2Name.put(NOT_AUDIT, "未发布");
				status2Name.put(ONLINE, "已发布");
				status2Name.put(OFFLINE, "已下线");
			}
			
			public static String getStatusName(byte status) {
				String statusName = status2Name.get(status);
				if(statusName == null) {
					return "未知状态";
				}
				return statusName;
			}
			
			public static String getStatusName(int status) {
				String statusName = status2Name.get((byte)status);
				if(statusName == null) {
					return "未知状态";
				}
				return statusName;
			}
			
		}
	}
	
	/**
	 * 视频转码格式
	 * 
	 * @author Administrator
	 *
	 */
	public static final class VOD_DEFINITION {
		public static final class NAME {
			public static final String VOD_ORIGINAL_FILE_URL = "definition0";// 原始
			public static final String VOD_PHONE_MP4_URL = "definition10"; // 手机，MP4
			public static final String VOD_NORMAL_MP4_URL = "definition20"; // 标清，MP4
			public static final String VOD_HIGH_MP4_URL = "definition30"; // 高清，MP4
			// public static final String VOD_SUPER_MP4_URL = "";
			public static final String VOD_PHONE_HLS_URL = "definition210"; // 手机，HLS
			public static final String VOD_NORMAL_HLS_URL = "definition220";// 标清，HLS
			public static final String VOD_HIGH_HLS_URL = "definition230"; // 高清，HLS
			public static final String VOD_SUPER_HLS_URL = "definition240"; // 超清，HLS
			// public static final String VOD_PHONE_FLV_URL = "";
			// public static final String VOD_NORMAL_FLV_URL = "";
			// public static final String VOD_HIGH_FLV_URL = "";
		}

		public static final String ConvertName(String definition) {
			if (definition.equals(NAME.VOD_ORIGINAL_FILE_URL)) {
				return "vod_original_file_url";
			} else if (definition.equals(NAME.VOD_PHONE_MP4_URL)) {
				return "vod_phone_mp4_url";
			} else if (definition.equals(NAME.VOD_NORMAL_MP4_URL)) {
				return "vod_normal_mp4_url";
			} else if (definition.equals(NAME.VOD_HIGH_MP4_URL)) {
				return "vod_high_mp4_url";
			} else if (definition.equals(NAME.VOD_PHONE_HLS_URL)) {
				return "vod_phone_hls_url";
			} else if (definition.equals(NAME.VOD_NORMAL_HLS_URL)) {
				return "vod_normal_hls_url";
			} else if (definition.equals(NAME.VOD_HIGH_HLS_URL)) {
				return "vod_high_hls_url";
			} else if (definition.equals(NAME.VOD_SUPER_HLS_URL)) {
				return "vod_super_hls_url";
			}
			return "";
		}
	}
	
	/**
	 * 上传文件的类型
	 */
	public static final class UploadFileRule {
		public static final String TYPE_PIC = "pic";
		public static final String TYPE_AUDIO = "audio";
		public static final String TYPE_VIDEO = "video";
		public static final String TYPE_ZIP = "zip";

		static class FileOption {
			private long size;
			private String format;
			private Set<String> formatSet;

			public FileOption() {
				formatSet = new HashSet<>();
			}

			public FileOption(long size, String format) {
				this();
				this.size = size;
				this.format = format;
				this.toFormatSet();
			}

			private void toFormatSet() {
				if(this.format != null) {
					formatSet.addAll(Arrays.asList(this.format.split(",")));
				}
			}
			public long getSize() {
				return size;
			}

			public void setSize(long size) {
				this.size = size;
			}

			public String getFormat() {
				return format;
			}

			public void setFormat(String format) {
				this.format = format;
				this.toFormatSet();
			}

			public boolean isValidFormat(String format) {
				if(format == null) {
					return false;
				}
				return formatSet.contains(format);
			}
		}

		private static Map<String, FileOption> fileRule = new HashMap<>();
		private static Set<String> pathSet = new HashSet<>();

		static {
			// file format should
			fileRule.put(TYPE_PIC, new FileOption(1024 * 1024 * 1l, ".png,.bmp,.jpg,.jpeg"));
			fileRule.put(TYPE_AUDIO, new FileOption(1024 * 1024 * 5l, ".mp3,.wav,.wma,.ogg,.ape,.acc"));
			fileRule.put(TYPE_VIDEO, new FileOption(1024 * 1024 * 20l, ".rm,.rmvb,.avi,.mp4,.3gp"));
			fileRule.put(TYPE_ZIP, new FileOption(1024 * 1024 * 5l, ".zip"));

//			pathSet.add("crm");
//			pathSet.add("business");
//			pathSet.add("poi");
//			pathSet.add("article");
		}

		public static Set<String> getValidFileTypes() {
			return fileRule.keySet();
		}

		public static String getValidFileFormat(String fileType) {
			FileOption fileOption = fileRule.get(fileType);
			if(fileOption != null) {
				return fileOption.getFormat();
			}
			return null;
		}
		public static boolean isValidFileType(String fileType) {
			return fileRule.containsKey(fileType);
		}

		public static boolean isValidSize(String fileType, long size) {
			FileOption fileOption = fileRule.get(fileType);
			if(fileOption != null) {
				return size < fileOption.getSize();
			}
			return false;
		}

		public static boolean isValidFormat(String fileType, String format) {
			FileOption fileOption = fileRule.get(fileType);
			if(fileOption != null) {
				return fileOption.isValidFormat(format);
			}
			return false;
		}

//		public static boolean isValidPath(String path) {
//			return pathSet.contains(path);
//		}

		public static String getFileExtention(String fileName) {
			if(fileName == null) {
				return "";
			}
			int idx = fileName.lastIndexOf(".");
			if(idx > 0) {
				return fileName.substring(idx);
			}
			return "";
		}
	}

	public static final class FieldTypes {
		public static final String TEXT = "TEXT";
		public static final String TEXTAREA = "TEXTAREA";
		public static final String PIC = "PIC";
		public static final String AUDIO = "AUDIO";
		public static final String VIDEO = "VIDEO";
		public static final String RADIOBUTTON = "RADIOBUTTON";
		public static final String CHECKBOX = "CHECKBOX";
		public static final String POINT = "POINT";
		public static final String SELECT = "SELECT";
		public static final String TEXT_PIC = "TEXT_PIC_GROUP";
		public static final String COUNTRY_ENJOYMENT = "COUNTRY_ENJOYMENT";
		public static final String RADIO_TEXT = "RADIO_TEXT";
	}

}
