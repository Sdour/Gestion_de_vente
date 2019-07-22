package DAO;

import java.util.Date;

public abstract class AbstractCommand implements java.io.Serializable {

	private Integer codecmd;
	private Integer codeart;
	private Integer codeclient;
	private Long quantitecmd;
	private Date datecmd;

	public AbstractCommand() {
	}

	public AbstractCommand(Integer codecmd, Integer codeart, Integer codeclient) {
		this.codecmd = codecmd;
		this.codeart = codeart;
		this.codeclient = codeclient;
	}

	public AbstractCommand(Integer codecmd, Integer codeart, Integer codeclient, Long quantitecmd, Date datecmd) {
		this.codecmd = codecmd;
		this.codeart = codeart;
		this.codeclient = codeclient;
		this.quantitecmd = quantitecmd;
		this.datecmd = datecmd;
	}

	public Integer getCodecmd() {
		return this.codecmd;
	}

	public void setCodecmd(Integer codecmd) {
		this.codecmd = codecmd;
	}

	public Integer getCodeart() {
		return this.codeart;
	}

	public void setCodeart(Integer codeart) {
		this.codeart = codeart;
	}

	public Integer getCodeclient() {
		return this.codeclient;
	}

	public void setCodeclient(Integer codeclient) {
		this.codeclient = codeclient;
	}

	public Long getQuantitecmd() {
		return this.quantitecmd;
	}

	public void setQuantitecmd(Long quantitecmd) {
		this.quantitecmd = quantitecmd;
	}

	public Date getDatecmd() {
		return this.datecmd;
	}

	public void setDatecmd(Date datecmd) {
		this.datecmd = datecmd;
	}

}