package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class Regex {

	public boolean RegexDiaChi(JTextField txtDiaChi2) {
		String input = txtDiaChi2.getText();
		String regex = "^([ A-Za-z0-9,.a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]*(\\s?))+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find()) {
			JOptionPane.showMessageDialog(null, "Nhập sai địa chỉ (Ví dụ nhập:56a Cầu Xéo, Tân quí, Tân Phú");
			txtDiaChi2.requestFocus();
			txtDiaChi2.selectAll();
			return false;
		} else
			return true;
	}


	public boolean RegexTen(JTextField txtTen2) {
		String input = txtTen2.getText();
		String regex = "^([ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]*(\\s?))+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find()) {
			JOptionPane.showMessageDialog(null, "Nhập sai tên (Ví dụ nhập:Nguyễn Văn A)");
			txtTen2.requestFocus();
			txtTen2.selectAll();
			return false;
		} else
			return true;
	}

	public boolean kiemTraRong(JTextField txt) {
		if (txt.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Dữ liệu không được để trống");
			txt.requestFocus();
			return false;
		}
		return true;
	}

	public boolean kiemTraLuongNV(JTextField txtTuoi2) {
		try {
			int x = Integer.parseInt(txtTuoi2.getText());
			if (x < 0) {
				JOptionPane.showMessageDialog(null, "Nhập sai dữ liệu lương (Phải lớn hơn 0)");
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Nhập sai kiểu dữ liệu lương (phải nhập số)");
			return false;
		}
	}

	public boolean RegexSDT(JTextField txtSDT) {
		String input = txtSDT.getText();
		String regex = "^[0][0-9]{9}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find()) {
			JOptionPane.showMessageDialog(null, "Nhập sai số điện thoại (Ví dụ nhập:0987654321");
			txtSDT.requestFocus();
			txtSDT.selectAll();
			return false;
		} else
			return true;
	}
//	SỐ LƯỢNG
	public boolean regexSoLuong(JTextField txtSoluong) {
		String input = txtSoluong.getText();
		String regex = "^[0-9]+$";
		if(!input.matches(regex))
		{	JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên và lớn hơn 0 (ví dụ nhập: 1000)");
			txtSoluong.requestFocus();
			txtSoluong.selectAll();
			return false;
		}
		return true;
		
	}
	public boolean kiemTraNgaySinh(JDateChooser ngaySinh) {
		long time = (System.currentTimeMillis() - ngaySinh.getDate().getTime());
		double yearsBetween = time / 3.15576e+10;
		int age = (int) Math.floor(yearsBetween);
		
		if(age < 18) {
			JOptionPane.showMessageDialog(null, "Ngày sinh không hợp lệ!\nTuổi phải lớn hơn hoặc bằng 18");
			return false;
		}
		return true;
		
	}

}
