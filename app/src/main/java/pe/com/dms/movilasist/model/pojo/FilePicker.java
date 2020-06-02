package pe.com.dms.movilasist.model.pojo;

import java.io.File;

import pe.com.dms.movilasist.annotacion.media.ChooserPickerType;
import pe.com.dms.movilasist.annotacion.media.FilePickerType;

public class FilePicker {

    private File file;
    private String path;
    private String mimeType;
    private String album;
    private String filename;
    private long size;
    private String formatSize;
    private long lastModified;
    private String thumbnail;           //Image or Video
    private long duration;              //Audio or Video
    @FilePickerType
    private int type;                   //Type of file
    @ChooserPickerType
    private int chooserType;            //Gallery or Documents
    private boolean selected;           //Only for adapters checked

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFormatSize() {
        return formatSize;
    }

    public void setFormatSize(String formatSize) {
        this.formatSize = formatSize;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChooserType() {
        return chooserType;
    }

    public void setChooserType(int chooserType) {
        this.chooserType = chooserType;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "FilePicker{" +
                "file=" + file +
                ", path='" + path + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", album='" + album + '\'' +
                ", filename='" + filename + '\'' +
                ", size=" + size +
                ", formatSize='" + formatSize + '\'' +
                ", lastModified=" + lastModified +
                ", thumbnail='" + thumbnail + '\'' +
                ", duration=" + duration +
                ", type=" + type +
                ", chooserType=" + chooserType +
                ", selected=" + selected +
                '}';
    }
}
