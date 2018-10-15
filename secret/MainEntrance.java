package secret;

import java.util.Scanner;

public class MainEntrance {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			for (int i=0;i<4;i++) {
				System.out.println("请选择加密或者解密。0：加密 1：解密");
				String string = sc.nextLine(); 
				int type =  Integer.parseInt(string);
				if (type==1 || type==0) {
					switch(type) 
					{ 
					case 0:
						System.out.println("请输入需要加密机器的ip");
						String inputIp = sc.nextLine();
						System.out.println(EncryptionSha.getResult(inputIp));
						break;
					case 1:
						System.out.println("请输入需要加密机器的ip");
						String inputIp2 = sc.nextLine();
						System.out.println("请输入加密的盐值。");
						String inputSalt = sc.nextLine();
						String str = SelfBase64.toEncodingBase64(inputSalt);
						System.out.println(EncryptionSha.sha(inputIp2+str));
						break;
					default:
					} 
					break;
				}	else {
					System.out.println("只能输入0或者1，请重新输入。");
				}
			}
		} catch (Exception e) {
			System.out.println("读取键盘输入失败.");
		}
		finally {
			sc.close();
		}
		
	}
	

}