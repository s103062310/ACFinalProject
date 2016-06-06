package main;

import java.io.File;
import java.net.URI;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

/**
 * ���T���񾹡A�䴩WAV�BAIFF�BAU�榡
 *
 * @author magiclen
 */
public class AudioPlayer {

    /**
     * ���T���񾹪����A
     */
    public static enum Status {

	OPEN, START, STOP, CLOSE;
    }

    public static interface StatusChangedListener {

	public void statusChanged(Status status);
    }

    //-----�����ܼ�-----
    private AudioInputStream audioInputStream;
    private AudioFormat audioFormat;
    private DataLine.Info dataLineInfo;
    private Clip clip;
    private int playCount = 1, playCountBuffer = 1;
    private int volume = 50, balance = 0;
    private Status status = null;
    private boolean autoClose = false;
    private StatusChangedListener statusListener;

    //-----�غc�l-----
    /**
     * �غc�l�A�ǤJ�ɮ�
     *
     * @param file �ǤJ�n���ɮ�
     */
    public AudioPlayer(final File file) {
	try {
	    final URL url = file.toURI().toURL();
	    init(url);
	} catch (final Exception ex) {
	    throw new RuntimeException(ex.getMessage());
	}

    }

    /**
     * �غc�l�A�ǤJURL
     *
     * @param url �ǤJ�n��URL
     */
    public AudioPlayer(final URL url) {
	try {
	    init(url);
	} catch (final Exception ex) {
	    throw new RuntimeException(ex.getMessage());
	}
    }

    /**
     * �غc�l�A�ǤJURL String
     *
     * @param str �ǤJ�n��URL String
     */
    public AudioPlayer(final String str) {
	try {
	    final URL url = URI.create(str).toURL();
	    init(url);
	} catch (final Exception ex) {
	    throw new RuntimeException(ex.getMessage());
	}
    }

    //-----��l��-----
    /**
     * ��l��AudioPlayer
     *
     * @param url �ǤJ�n��URL
     * @throws Exception �ߥX�ҥ~
     */
    private void init(final URL url) throws Exception {
	//Ū�����ֿ�J��y
	try {
	    audioInputStream = AudioSystem.getAudioInputStream(url);
	} catch (final Exception ex) {
	    throw new RuntimeException(ex.getMessage());
	}
	//�i�漽��]�w
	audioFormat = audioInputStream.getFormat();
	int bufferSize = (int) Math.min(audioInputStream.getFrameLength() * audioFormat.getFrameSize(), Integer.MAX_VALUE); //�w�Ĥj�p�A�p�G���T�ɮפ��j�A�i�H�����s�J�w�ĪŶ��C�o�Ӽƭ����ӭn���ӥγ~�ӨM�w
	dataLineInfo = new DataLine.Info(Clip.class, audioFormat, bufferSize);
	clip = (Clip) AudioSystem.getLine(dataLineInfo);
	clip.addLineListener(e -> {
	    LineEvent.Type type = e.getType();
	    if (type.equals(LineEvent.Type.START)) {
		status = Status.START;
	    } else if (type.equals(LineEvent.Type.STOP)) {
		status = Status.STOP;
		if (clip.getFramePosition() == clip.getFrameLength()) {
		    clip.setFramePosition(0);
		}
		if (playCount == 0 || (playCount > 0 && playCountBuffer < playCount)) {
		    playCountBuffer++;
		    clip.start();
		} else {
		    playCountBuffer = 1;
		    if (autoClose) {
			clip.close();
		    }
		}
	    } else if (type.equals(LineEvent.Type.OPEN)) {
		status = Status.OPEN;
	    } else if (type.equals(LineEvent.Type.CLOSE)) {
		status = Status.CLOSE;
	    } else {
		return;
	    }
	    if (statusListener != null) {
		statusListener.statusChanged(status);
	    }
	});
	clip.open(audioInputStream);
	setVolume();
	setBalance();
    }

    /**
     * �}�l���񭵰T�A�i�H�^�_�Ȱ��ɪ����A
     */
    public void play() {
	clip.start();
    }

    /**
     * �Ȱ����񭵰T
     */
    public void pause() {
	clip.stop();
    }

    /**
     * ����񭵰T�A�U������N�|���Y�}�l
     */
    public void stop() {
	clip.stop();
	clip.setFramePosition(0);
    }

    /**
     * �]�w���񦸼ơA0���L��������
     *
     * @param playCount �ǤJ���񦸼�
     */
    public void setPlayCount(final int playCount) {
	if (playCount < 0) {
	    throw new RuntimeException("PlayCount must be at least 0!");
	}
	this.playCount = playCount;
    }

    /**
     * �]�w�R��
     */
    public void mute() {
	setVolume(0);
    }

    /**
     * �O�_�R��
     *
     * @return �Ǧ^�O�_�R��
     */
    public boolean isMute() {
	return volume == 0;
    }

    /**
     * �]�w���q�A�d��O0~100�A�ƭȷU�j�U�j�n
     *
     * @param volume �ǤJ���q
     */
    public void setVolume(final int volume) {
	if (volume < 0 || volume > 100) {
	    throw new RuntimeException("Volumn must be at least 0 and at most 100!");
	}
	this.volume = volume;
	setVolume();
    }

    /**
     * ���o���q
     *
     * @return �Ǧ^���q
     */
    public int getVolume() {
	return volume;
    }

    /**
     * �]�w���q
     */
    private void setVolume() {
	final FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	final float db = (float) (Math.log10(volume * 0.039) * 10);
	floatControl.setValue(db);
    }

    /**
     * ���o�ثe���T���񾹪����A
     *
     * @return �Ǧ^���A
     */
    public Status getStatus() {
	return status;
    }

    /**
     * �u�}�ҥk�n�D
     */
    public void onlyRight() {
	setBalance(100);
    }

    /**
     * �O�_�u�}�ҥk�n�D
     *
     * @return �Ǧ^�O�_�u�}�ҥk�n�D
     */
    public boolean isOnlyRight() {
	return balance == 100;
    }

    /**
     * �u�}�ҥ��n�D
     */
    public void onlyLeft() {
	setBalance(-100);
    }

    /**
     * �O�_�u�}�ҥ��n�D
     *
     * @return �Ǧ^�O�_�u�}�ҥ��n�D
     */
    public boolean isOnlyLeft() {
	return balance == -100;
    }

    /**
     * �]�w�n�D���q�����Ū��A
     */
    public void balance() {
	setBalance(0);
    }

    /**
     * �n�D���q�O�_�����Ū��A
     *
     * @return �Ǧ^�n�D���q�O�_�����Ū��A
     */
    public boolean isBalance() {
	return balance == 0;
    }

    /**
     * �]�w�n�D���q�����šA�d��-100~100�A�ƭȷU�j�U�a��k��A0�����Ū��A
     *
     * @param balance �ǤJ�n�D���q�����ŭ�
     */
    public void setBalance(final int balance) {
	if (volume < 0 || volume > 100) {
	    throw new RuntimeException("Balance must be at least -100 and at most 100!");
	}
	this.balance = balance;
	setBalance();
    }

    /**
     * ���o�n�D���q�����ŭ�
     *
     * @return �Ǧ^�n�D���q�����ŭ�
     */
    public int getBalance() {
	return balance;
    }

    /**
     * �]�w�n�D���q�����ŭ�
     */
    private void setBalance() {
	try {
	    final FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
	    final float pan = balance / 100.0f;
	    floatControl.setValue(pan);
	} catch (final Exception ex) {
	    //�i��O���n�D���T�ɳy�����ҥ~
	}
    }

    /**
     * ���o���T������(�L��)
     *
     * @return �Ǧ^���T������
     */
    public long getAudioLength() {
	return clip.getMicrosecondLength();
    }

    /**
     * ���o���T�ثe����m(�L��)
     *
     * @return �Ǧ^���T�ثe����m
     */
    public long getAudioPosition() {
	return clip.getMicrosecondPosition();
    }

    /**
     * �]�w���T����m(�L��)
     *
     * @param position �ǤJ���T����m
     *
     */
    public void setAudioPosition(long position) {
	clip.setMicrosecondPosition(position);
    }

    /**
     * �������T
     */
    public void close() {
	clip.close();
    }

    /**
     * �]�w���񵲧���O�_�۰�����
     *
     * @param autoClose �ǤJ���񵲧���O�_�۰�����
     */
    public void setAutoClose(final boolean autoClose) {
	this.autoClose = autoClose;
    }

    /**
     * ���o���񵲧���O�_�۰�����
     *
     * @return �Ǧ^���񵲧���O�_�۰�����
     */
    public boolean isAutoClose() {
	return autoClose;
    }

    /**
     * �]�w���A���ܫ᪺��ť�ƥ�
     *
     * @param listener �ǤJ���A���ܪ���ť�ƥ�
     */
    public void setStatusChangedListener(StatusChangedListener listener) {
	this.statusListener = listener;
    }

    /**
     * ���o���A���ܫ᪺��ť�ƥ�
     *
     * @return �Ǧ^���A���ܫ᪺��ť�ƥ�
     */
    public StatusChangedListener getStatusChangedListener() {
	return statusListener;
    }
}
