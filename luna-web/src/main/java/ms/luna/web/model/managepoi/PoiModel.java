package ms.luna.web.model.managepoi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import ms.luna.web.model.common.SimpleModel;

public class PoiModel implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1931358294818493058L;

	/**
	 * mongo的ID(_id)也是POI的ID
	 */
	private String poiId;
	
	/**
	 * 别名
	 */
	@Size(message="{{shortName.length}", max=64)
	@NotBlank(message="{shortName.empty}")
	@Pattern(message="{shortName.char}", regexp="/[^\u4e00-\u9fa5A-Za-z_0-9]+/g")
	private String shortName = null;

	/**
	 * 全景数据ID
	 */
	private String panorama = null;

	/**
	 * 联系电话
	 */
	private String contact_phone = null;

	/**
	 * 名称
	 */
	@Size(message="{longName.length}", max=64)
	@NotBlank(message="{longName.empty}")
	@Pattern(message="{longName.char}", regexp="/[^\u4e00-\u9fa5A-Za-z_0-9]+/g")
	private String longName = null;

	/**
	 * 类别(选择值)
	 */
	@NotEmpty(message="{checkeds.empty}")
	private List<String> checkeds = null;

	/**
	 * 一级标签
	 */
	private Integer topTag;

	/**
	 * 二级标签
	 */
	private Integer subTag;

	/**
	 * 类别(POI TAG)
	 */
	private List<SimpleModel> poiTags = new ArrayList<SimpleModel>();

	/**
	 * 纬度
	 */
	@Digits(message="{lat.range}", fraction = 6, integer = 3)
	@NotNull(message="{lat.empty}")
	private BigDecimal lat;

	/**
	 * 经度
	 */
	@Digits(message="{lng.range}", fraction = 6, integer = 3)
	@NotNull(message="{lng.empty}")
	private BigDecimal lng;

	/**
	 * 详细地址
	 */
	@Size(message="{address.length}", max=255)
	private String detailAddress;

	/**
	 * 国家
	 */
	@NotBlank(message="{country.empty}")
	private String countryId;
	/**
	 * 省份
	 */
	@NotBlank(message="{province.empty}")
	private String provinceId;
	/**
	 * 城市
	 */
	@NotBlank(message="{city.empty}")
	private String cityId;
	/**
	 * 区/县
	 */
	private String countyId;
	/**
	 * 介绍(简介)
	 */
	@NotEmpty(message="{briefIntroduction.empty}")
	@Size(message="{briefIntroduction.length}", max=255)
	private String briefIntroduction;

	/**
	 * 缩略图地址
	 */
	@NotEmpty(message="{thumbnail.empty}")
	@Size(message="{thumbnail.length}", max=255)
	@Pattern(message="{thumbnail.char}", regexp="/^http:\\/\\/.*?\\/.*?\\.(jpg|jpeg|png)/i")
	private String thumbnail;

	/**
	 * 规格1
	 */
	private Boolean specifications1;

	/**
	 * 规格2
	 */
	private Boolean specifications2;

	/**
	 * 音频地址
	 */
	@Size(message="{audio.length}", max=255)
	@Pattern(message="{audio.char}", regexp="/^http:\\/\\/.*?\\/.*?\\.(mp3|wav)/i")
	private String audio;
	
	/**
	 * 音频地址
	 */
	@Size(message="{video.length}", max=255)
	@Pattern(message="{video.char}", regexp="/^http:\\/\\/.*?\\/.*?\\.(mp4)/i")
	private String video;

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**
	 * @return the checkeds
	 */
	public List<String> getCheckeds() {
		return checkeds;
	}

	/**
	 * @param checkeds the checkeds to set
	 */
	public void setCheckeds(List<String> checkeds) {
		this.checkeds = checkeds;
	}

	/**
	 * @return the poiTags
	 */
	public List<SimpleModel> getPoiTags() {
		return poiTags;
	}

	/**
	 * @param poiTags the poiTags to set
	 */
	public void setPoiTags(List<SimpleModel> poiTags) {
		this.poiTags = poiTags;
	}

	/**
	 * @return the lat
	 */
	public BigDecimal getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public BigDecimal getLng() {
		return lng;
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	/**
	 * @return the briefIntroduction
	 */
	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	/**
	 * @param briefIntroduction the briefIntroduction to set
	 */
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	/**
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return the specifications1
	 */
	public Boolean getSpecifications1() {
		return specifications1;
	}

	/**
	 * @param specifications1 the specifications1 to set
	 */
	public void setSpecifications1(Boolean specifications1) {
		this.specifications1 = specifications1;
	}

	/**
	 * @return the specifications2
	 */
	public Boolean getSpecifications2() {
		return specifications2;
	}

	/**
	 * @param specifications2 the specifications2 to set
	 */
	public void setSpecifications2(Boolean specifications2) {
		this.specifications2 = specifications2;
	}

	/**
	 * @return the audio
	 */
	public String getAudio() {
		return audio;
	}

	/**
	 * @param audio the audio to set
	 */
	public void setAudio(String audio) {
		this.audio = audio;
	}

	/**
	 * @return the video
	 */
	public String getVideo() {
		return video;
	}

	/**
	 * @param video the video to set
	 */
	public void setVideo(String video) {
		this.video = video;
	}

	/**
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the countyId
	 */
	public String getCountyId() {
		return countyId;
	}

	/**
	 * @param countyId the countyId to set
	 */
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	/**
	 * @return the detailAddress
	 */
	public String getDetailAddress() {
		return detailAddress;
	}

	/**
	 * @param detailAddress the detailAddress to set
	 */
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	/**
	 * @return the poiId
	 */
	public String getPoiId() {
		return poiId;
	}

	/**
	 * @param poiId the poiId to set
	 */
	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}

	/**
	 * @return the panorama
	 */
	public String getPanorama() {
		return panorama;
	}

	/**
	 * @param panorama the panorama to set
	 */
	public void setPanorama(String panorama) {
		this.panorama = panorama;
	}

	/**
	 * @return the contact_phone
	 */
	public String getContact_phone() {
		return contact_phone;
	}

	/**
	 * @param contact_phone the contact_phone to set
	 */
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	/**
	 * @return the topTag
	 */
	public Integer getTopTag() {
		return topTag;
	}

	/**
	 * @param topTag the topTag to set
	 */
	public void setTopTag(Integer topTag) {
		this.topTag = topTag;
	}

	/**
	 * @return the subTag
	 */
	public Integer getSubTag() {
		return subTag;
	}

	/**
	 * @param subTag the subTag to set
	 */
	public void setSubTag(Integer subTag) {
		this.subTag = subTag;
	}

}
