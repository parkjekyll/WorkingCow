package com.wc.pb.customer.controller;

import java.io.UnsupportedEncodingException;
import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wc.pb.admin.service.AdminService;
import com.wc.pb.common.util.FileSave;
import com.wc.pb.common.util.IpGet;
import com.wc.pb.customer.service.CustomerService;
import com.wc.pb.customer.vo.CashVO;
import com.wc.pb.customer.vo.CouponVO;
import com.wc.pb.customer.vo.CustomerImgVO;
import com.wc.pb.customer.vo.CustomerVO;
import com.wc.pb.customer.vo.CustomerWalletVO;
import com.wc.pb.customer.vo.EmailAuthVO;
import com.wc.pb.customer.vo.LikeVO;
import com.wc.pb.customer.vo.MileageVO;
import com.wc.pb.customer.vo.OrderVO;
import com.wc.pb.customer.vo.ReviewVO;
import com.wc.pb.customer.vo.SellerAuthVO;
import com.wc.pb.customer.vo.SellerRegistImgVO;
import com.wc.pb.log.vo.LoginLogVO;
import com.wc.pb.seller.service.ProductService;
import com.wc.pb.seller.service.SellerService;
import com.wc.pb.seller.vo.InquiryToSellerVO;
import com.wc.pb.seller.vo.ProductVO;
import com.wc.pb.seller.vo.SellerVO;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private FileSave fileSave;
	@Autowired
	private AdminService adminService;
	@Autowired
	private IpGet ipGet;

	//?????? ?????????
	@RequestMapping("mainPage.do")
	public String mainPage() {
		return "redirect:./";
	}
	//?????? ?????????
	@RequestMapping("terms")
	public String terms() {
		return "loginPage/loginPage_terms";
	}
	//???????????? ?????????
	@RequestMapping("join")
	public String join() {
		return "loginPage/loginPage_join";
	}
	//???????????? ?????????
	@RequestMapping("idCheckPage.do")
	public String idChkPage() {
		return "loginPage/loginPage_idCheck";
	}
	//????????? ????????????
	@RequestMapping("idCheckProcess.do")
	public String idChkProcess(HttpServletRequest request) {

		return "loginPage/loginPage_idCheck";
	}
	//????????? ????????????
	   @ResponseBody
	   @RequestMapping(value= "/nickChk.do", method = RequestMethod.POST)
	   public int nickChk(HttpServletRequest request) {
	      
	      String customer_nick = request.getParameter("customer_nick");
	      System.out.println(customer_nick);
	      
	      int result = customerService.nickChk(customer_nick);
	      System.out.println(result);

	      return result;
	   }
	//?????? ??????
	   @RequestMapping("joinProcess.do")
	   public String joinProcess(CustomerVO customerVO, HttpServletRequest request) {
	      HttpSession session = request.getSession();
	      //char marketing_agree = request.getParameter("marketing_agree").charAt(0);
	      //System.out.println(marketing_agree);
	      
	      EmailAuthVO emailAuthVO = new EmailAuthVO();
	      //????????? ?????? ??? ??????
	      
	      customerService.joinCustomer(customerVO);
	      
	      
	      int customer_no = customerService.getCustomerNo();
	      customerService.createCustomerWallet(customer_no);
	      int customer_wallet_no = customerService.customer_wallet_no();
	      customerService.createMyCash(customer_wallet_no);
	      customerService.createMyMileage(customer_wallet_no);
	      
	      emailAuthVO.setCustomer_no(customer_no);
	      customerService.getEmailAuth(emailAuthVO);
	      
	      HashMap<String, Object> joinLog = new HashMap<String, Object>();
	      String jlog_ip = ipGet.getUserIP(request);
	      joinLog.put("jlog_ip", jlog_ip);
	      joinLog.put("customer_no", customer_no);
	      customerService.insertJoinLog(joinLog);
	      
	      MailSendThread mailSendThread = new MailSendThread(mailSender, customerVO.getCustomer_email());
	      mailSendThread.start();
	      
	      return "redirect:./";
	   }
	
	// ????????? ?????? ???????????????
	class MailSendThread extends Thread {

		private JavaMailSender mailSender;
		private String mailAddress;

		public MailSendThread(JavaMailSender mailSender, String mailAddress) {
			super();
			this.mailSender = mailSender;
			this.mailAddress = mailAddress;
		}

		@Override
		public void run() {
			// ?????? api??????
			MimeMessage message = null;
			MimeMessageHelper messageHelper = null;
			message = mailSender.createMimeMessage();
			try {
				messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setSubject("[????????? ????????????]??????????????? ????????? ????????? ?????? ???????????????.");
				String text = "<a href='http://localhost:8080/web/updateEmailAuth.do'>";
				text += "?????? ????????????";
				text += "</a>";

				messageHelper.setText(text, true);
				messageHelper.setFrom("admin", "?????????");
				messageHelper.setTo(mailAddress);
				mailSender.send(message);

			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@RequestMapping("updateEmailAuth.do")
	public String updateEmailAuth() {
		customerService.updateEmailAuth();
		return "loginPage/loginPage_emailAuth";
	}

	//????????? ?????????
	@RequestMapping("login")
	public String loginPage() {
		return "loginPage/loginPage_login";
	}

	//????????? ????????????
	   @RequestMapping("loginProcess.do")
	   public String loginCustomer(HttpSession session,HttpServletRequest request, CustomerVO customerVO) {
	      CustomerVO sessionCustomer = customerService.customerLogin(customerVO);

	      if (sessionCustomer != null) {
	         int customer_no = sessionCustomer.getCustomer_no();
	         System.out.println("cno:"+customer_no);
	         // ??????
	         session.setAttribute("sessionCustomer", sessionCustomer);
	         session.setAttribute("sessionCustomer_email", sessionCustomer.getCustomer_email());
	         session.setAttribute("sessionCustomer_password", sessionCustomer.getCustomer_password());
	         session.setAttribute("sessionCustomer_nick", sessionCustomer.getCustomer_nick());
	         session.setAttribute("sessionCustomer_no", sessionCustomer.getCustomer_no());
	         
	         //????????? ?????? ??????
	         LoginLogVO logsuccess = new LoginLogVO();
	         logsuccess.setCustomer_no(customer_no);
	         logsuccess.setLoginLog_customerId(session.getAttribute("sessionCustomer_nick").toString());
	         logsuccess.setLoginLog_success('Y');
	         logsuccess.setLoginLog_ip(ipGet.getUserIP(request));
	         adminService.loginLog(logsuccess);
	         //????????? ?????? ??????
	         int result = customerService.checkEmailAuth(customer_no);
	         System.out.println("result:"+result);
	         
	         if(result==1){
	            return "redirect:/";
	         
	         }else{
	            // ??????
	            return "redirect:./login?error=2";
	         }
	      }else {
	    	  String email = customerService.customerLoginReason_email(customerVO);
	        	//????????? ?????? ??????
		         LoginLogVO logfail = new LoginLogVO();
		         logfail.setLoginLog_customerId(customerVO.getCustomer_email());
		         logfail.setLoginLog_success('N');
		         logfail.setLoginLog_ip(ipGet.getUserIP(request));
		         if(email == null) {
		        	 logfail.setLoginLog_reason("????????? ?????????");
		         }
		         if(email != null) {
		        	 logfail.setLoginLog_reason("???????????? ?????????");
		         }
		         adminService.loginLog(logfail);
	         return "redirect:./login?error=1";         
	      }

	   }
	//?????? ?????? ?????????
	@RequestMapping("findPW")
	public String findPW() {
		return "loginPage/loginPage_findPw";
	}
	
	@RequestMapping("findPwProcess.do")
	public String findPwProcess(String customer_email) {
		CustomerVO customerVO=customerService.findPw(customer_email);
		if(customerVO!=null) {
			// ???????????? ???????????? ????????? ???????????? ?????? ????????????
			MailSendThread1 mailSendThread = new MailSendThread1(mailSender, customerVO.getCustomer_email());
			mailSendThread.start();
			
			return "loginPage/loginPage_findPwEmailMSG";
		}
		return "loginPage/loginPage_findCustomerPwFail";
	}
	
	// ???????????? ?????? ???????????????
	class MailSendThread1 extends Thread {

		private JavaMailSender mailSender;
		private String mailAddress;

		public MailSendThread1(JavaMailSender mailSender, String mailAddress) {
			super();
			this.mailSender = mailSender;
			this.mailAddress = mailAddress;
		}

		@Override
		public void run() {
			// ?????? api??????
			MimeMessage message = null;
			MimeMessageHelper messageHelper = null;
			message = mailSender.createMimeMessage();
			try {
				messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setSubject("[???????????? ????????????]??????????????? ???????????? ????????? ?????? ???????????????.");
				String text = "<a href='http://localhost:8080/web/updatePwPage.do'>";
				text += "?????? ????????????";
				text += "</a>";

				messageHelper.setText(text, true);
				messageHelper.setFrom("admin", "?????????");
				messageHelper.setTo(mailAddress);
				mailSender.send(message);

			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//???????????? ????????????
	@RequestMapping("updatePwPage.do")
	public String updatePwPage() {
		return "loginPage/loginPage_updatePwPage";
	}
	
	@RequestMapping("updatePwProcess.do")
	   public String updatePwProcess(String customer_email, String customer_password, HttpSession session, HttpServletRequest request) {
	      //???????????? ????????????
	      CustomerVO customerVO=customerService.findPw(customer_email);
	      int customer_no = customerVO.getCustomer_no();
	      customerVO = customerService.selectByCustomerNo(customer_no);
	      String ex_password = customerVO.getCustomer_password();
	      String clog_ip = ipGet.getUserIP(request);
	      String clog_description = "????????????";
	      System.out.println("cno:"+customer_no);
	      System.out.println("clogdescription:"+clog_description);
	      System.out.println("expw:"+ex_password);
	      System.out.println("afpw:"+customer_password);
	      System.out.println("ip:"+clog_ip);
	      
	      HashMap<String, Object> map = new HashMap<String, Object>();
	      map.put("customer_no", customer_no);
	      map.put("clog_description",clog_description);
	      map.put("ex_password", ex_password);
	      map.put("customer_password", customer_password);
	      map.put("clog_ip", clog_ip);
	      
	      adminService.insertExPassword(map);
	      if(customerVO!=null) {
	         //????????????
	         customerService.updatePwByEmail(customer_email, customer_password);
	         return "loginPage/loginPage_pwChangeSuccess";
	      }
	      return "loginPage/loginPage_findCustomerPwFail";
	   }

	
	// ???????????? ????????????
	@RequestMapping("logoutProcess.do")
	public String logOut(HttpSession session) {
		session.invalidate();
		return "loginPage/loginPage_login";
	}
	//???????????? ??????
	@RequestMapping("changeMyInfoPage")
	public String changeMyInfoPage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
		int customer_no = sessionCustomer.getCustomer_no();
		
		HashMap<String, Object> InfoList = new HashMap<String, Object>();
		CustomerVO customerVO = customerService.selectByCustomerNo(customer_no);
		CustomerImgVO customerImgVO = customerService.selectImgByCustomerNo(customer_no);
		InfoList.put("customerImgVO", customerImgVO);
		InfoList.put("customerVO", customerVO);
		model.addAttribute("InfoList", InfoList);
		
		return "myPage/myPage_changeMyInfo";
	}

	//???????????? ????????? ?????? ?????? ????????????
	@RequestMapping("applyMyProfileImg.do")
	public String applyMyProfileImg(MultipartFile customer_imgOriFileName, HttpSession session, HttpServletRequest request) {
		System.out.println("?????? ??????");
		CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
		int customer_no = sessionCustomer.getCustomer_no();
		
		System.out.println(customer_imgOriFileName);
		
		if(customer_imgOriFileName.getOriginalFilename() != null && customer_imgOriFileName.getSize() > 0) {
			HashMap<String, Object> profile_img = new HashMap<String, Object>();
			String realPath = request.getSession().getServletContext().getRealPath("");
            String savePath = realPath + "resources/upload_files/";
			String realFileName = fileSave.save(savePath, customer_imgOriFileName);
			
			profile_img.put("customer_no", customer_no);
			profile_img.put("customer_imgOriFileName", customer_imgOriFileName.getOriginalFilename());
			profile_img.put("customer_imgName", realFileName);
			profile_img.put("customer_imgLocation", savePath);

			int result = customerService.selectImgNoByCustomerNo(customer_no);
			System.out.println(result);
			//?????? ???????????? ????????? ????????? ????????????
			//?????? ????????? ????????? ?????? ????????????
			if(result == 0) {
				customerService.applyMyProfileImg(profile_img);				
			}else {
				customerService.deleteCustomerProfileImg(customer_no);
				customerService.applyMyProfileImg(profile_img);
			}
			
		} else {
			System.out.println("????????? ????????? ??????");
			return "redirect:./changeMyInfoPage";
		}
		return "redirect:./changeMyInfoPage";
	}
	//???????????? ????????? ?????? ????????????
	   @RequestMapping("changeNickProcess.do")
	   public String changeNickProcess(HttpSession session, HttpServletRequest request) {
	      //?????? ????????????
	      CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
	      //???????????? ????????? ??????
	      String customer_email= sessionCustomer.getCustomer_email();
	      String customer_nick = request.getParameter("customer_nick");
	      CustomerVO customerVO = new CustomerVO();
	      customerVO.setCustomer_email(customer_email);
	      customerVO.setCustomer_nick(customer_nick);
	      int customer_no = sessionCustomer.getCustomer_no();
	      String ex_nick = sessionCustomer.getCustomer_nick();
	      String clog_description = "????????? ??????";
	      String clog_ip = ipGet.getUserIP(request);
	      
	      HashMap<String, Object> map = new HashMap<String, Object>();
	      map.put("customer_no", customer_no);
	      map.put("clog_description",clog_description);
	      map.put("ex_nick", ex_nick);
	      map.put("customer_nick", customer_nick);
	      map.put("clog_ip", clog_ip);
	      System.out.println(customer_no);
	      System.out.println(clog_description);
	      System.out.println(ex_nick);
	      System.out.println(customer_nick);
	      System.out.println(clog_ip);
	      
	      adminService.insertExNick(map);
	      
	      customerService.changeNick(customer_email, customer_nick);
	      
	      
	      return "redirect:./changeMyInfoPage";
	   }
	//???????????? ?????? ????????? ??????
	@ResponseBody
	@RequestMapping("checkMyPassword.do")
	public int checkMyPassword(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//?????? ????????????
		CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
		//???????????? ????????? ??????
		String customer_email= sessionCustomer.getCustomer_email();
		CustomerVO customerVO = new CustomerVO();
		//????????? ??? ??????
		customerVO.setCustomer_email(customer_email);
		customerVO.setCustomer_password(request.getParameter("customer_password"));
		
		int pwChk = customerService.pwChk(customerVO);
		System.out.println("????????? :"+pwChk);
		System.out.println("????????? ????????? :"+customer_email);
		System.out.println("???????????? ????????? ?????? :"+(request.getParameter("customer_password")));
		
		return pwChk;
	}
	//???????????? ?????? ?????? ????????????
	@RequestMapping("changeMyPasswordProcess.do")
	public String changeMyPasswordProcess(HttpServletRequest request, HttpSession session) {
		//?????? ????????????
		CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
		//???????????? ????????? ??????
		String customer_email= sessionCustomer.getCustomer_email();
		CustomerVO customerVO = new CustomerVO();
		//????????? ??? ??????
		customerVO.setCustomer_email(customer_email);	
		String customer_password = request.getParameter("customer_passwordModify");
		customerVO.setCustomer_password(customer_password);
		customerService.changeMyPassword(customer_password, customer_email);				
		
		return "redirect:./changeMyInfoPage";
	}
	//?????? ?????? ????????????
	@RequestMapping("withdrawalProcess.do")
	public String withdrawalProcess(HttpSession session) {
		//?????? ????????????
		CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
		//???????????? ????????? ??????
		String customer_email= sessionCustomer.getCustomer_email();
		CustomerVO customerVO = new CustomerVO();
		//????????? ??? ??????
		customerVO.setCustomer_email(customer_email);
		customerService.withdrawal(customer_email);
		session.invalidate();
		return "redirect:./";
	}
	//????????? ??????
	@RequestMapping("registSeller.do")
	public String registSeller(HttpSession session, MultipartFile sellerImg_OriFileName, HttpServletRequest request) {
		CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
		int customer_no = sessionCustomer.getCustomer_no();
		
		if(sellerImg_OriFileName.getOriginalFilename() != null && sellerImg_OriFileName.getSize() > 0) {
			HashMap<String, Object> seller_doc = new HashMap<String, Object>();

			String realPath = request.getSession().getServletContext().getRealPath("");
            String savePath = realPath + "resources/upload_files/";
			String realFileName = fileSave.save(savePath, sellerImg_OriFileName);
			
			seller_doc.put("customer_no", customer_no);
			seller_doc.put("sellerImg_OriFileName", sellerImg_OriFileName.getOriginalFilename());
			seller_doc.put("sellerImg_OriFileName2", sellerImg_OriFileName.getOriginalFilename());
			seller_doc.put("sellerImg_name", realFileName);
			seller_doc.put("sellerImg_location", savePath);
			customerService.registSeller(seller_doc);
		} else {
			System.out.println("????????? ????????? ??????");
			return "myPage/myPage_sellerRegist";
		}
		
		customerService.updateCustomerGrade(customer_no);
		customerService.insertSellerApply(customer_no);
		
		return "myPage/myPage_sellerRegist";
	}
	//????????? ?????? ?????? ?????????
	@GetMapping("myPage_sellerRegist")
	public String RegistStatus(HttpSession session,HttpServletRequest request, Model model) {
		if(session.getAttribute("sessionCustomer") != null) {
			
			CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
			int customer_no = sessionCustomer.getCustomer_no();
			HashMap<String, Object> map = new HashMap<String, Object>();
			CustomerVO customerVO = customerService.selectByCustomerNo(customer_no);
			SellerAuthVO sellerAuthVO = customerService.selectRegistStatus(customer_no);
			//ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
			map.put("sellerAuthVO", sellerAuthVO);
			map.put("customerVO", customerVO);
			int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
			int myCouponCount = customerService.myCouponCount(customer_walletNo);
			int myCash = customerService.myCash(customer_walletNo);
			int myMileage = customerService.myMileage(customer_walletNo);
			//????????????
			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("ip", ipGet.getUserIP(request));
			log.put("link", "???????????????-???????????????");
			if(session.getAttribute("sessionCustomer_nick") != null) {
				log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
			}
			adminService.accessLog(log);
			CustomerImgVO customerImg = customerService.customerDetail_img(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
			model.addAttribute("customerImg", customerImg);
			model.addAttribute("myCash", myCash);
			model.addAttribute("myMileage", myMileage);
			model.addAttribute("myCouponCount", myCouponCount);
			//result.add(map);
			model.addAttribute("data", map);
			return "myPage/myPage_sellerRegist";
		}
		return "redirect:./";
	}
	//?????? ????????????
	@RequestMapping("checkSellerApplyDoc.do")
	public String checkSellerApplyDoc(HttpSession session, Model model, HttpServletRequest request) {
		int seller_auth_no = (Integer.parseInt(request.getParameter("seller_auth_no")));
		CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
		int customer_no = sessionCustomer.getCustomer_no();
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerVO customerVO = customerService.selectByCustomerNo(customer_no);
		SellerAuthVO sellerAuthVO = customerService.selectRegistStatusByseller_auth_no(seller_auth_no);
		ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
		List<SellerRegistImgVO> sellerRegistImgList = customerService.selectSellerRegistImgListByNo(customer_no);
		for(SellerRegistImgVO sellerRegistImgVO : sellerRegistImgList) {	
			map.put("sellerRegistImgVO", sellerRegistImgVO);
			result.add(map);
		}
		
		map.put("customerVO", customerVO);
		map.put("sellerAuthVO", sellerAuthVO);
		
		model.addAttribute("result", result);
		model.addAttribute("data", map);
		
		return "myPage/myPage_sellerRegistDocDetail";
	}

	// ???????????????
	@RequestMapping("inquiryList.do")
	public String inquiryList(Model model, HttpSession session, String search_word, String search_type, HttpServletRequest request) {
		CustomerVO customerVO = (CustomerVO) session.getAttribute("sessionCustomer");
		if(customerVO.getCustomer_nick() != null) {
			int customer_no = customerVO.getCustomer_no();
			
			int page = 1;
			if(request.getParameter("page") !=null) page= Integer.parseInt(request.getParameter("page"));
			
			// ?????? ????????? ??????
			if(search_type != null && search_word != null || search_type != "" && search_word != "") {
				model.addAttribute("search_type", search_type);
				model.addAttribute("search_word", search_word);
			}
			int totalCount = customerService.InquiryTotalCount(customer_no);
			System.out.println(totalCount);
			ArrayList<InquiryToSellerVO> getInquiryList = customerService.searchInquiryList(search_word, search_type, customer_no, ((page-1)*10));
			List<HashMap<String, Object>> searchInquiryList = new ArrayList<HashMap<String, Object>>();
			for (InquiryToSellerVO inquiryToSellerVO : getInquiryList) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				customerVO = customerService.selectByCustomerNo(customer_no);
				
				int product_no = inquiryToSellerVO.getProduct_no();
				ProductVO productVO = productService.selectByProductNo(product_no);
				productVO.setProduct_no(product_no);
				
				map.put("inquiryToSellerVO", inquiryToSellerVO);
				map.put("customerVO", customerVO);
				map.put("productVO", productVO);
				
				searchInquiryList.add(map);
			}
			int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
			int myCouponCount = customerService.myCouponCount(customer_walletNo);
			int myCash = customerService.myCash(customer_walletNo);
			int myMileage = customerService.myMileage(customer_walletNo);
			CustomerImgVO customerImg = customerService.customerDetail_img(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
			model.addAttribute("customerImg", customerImg);
			model.addAttribute("searchInquiryList", searchInquiryList);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("page", page);
			model.addAttribute("myCouponCount", myCouponCount);
			model.addAttribute("myCash", myCash);
			model.addAttribute("myMileage", myMileage);
			//????????????
			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("ip", ipGet.getUserIP(request));
			log.put("link", "???????????????-?????????");
			if(session.getAttribute("sessionCustomer_nick") != null) {
				log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
			}
			adminService.accessLog(log);
			return "myPage/myPage_questionList";		
		}
		return "redirect:./";
	}

	// ?????? ?????????????????? ??????????????????
	@RequestMapping("readInquiryContent.do")
	public String readInquiryContent(Model model, int inquiry_no) {

		HashMap<String, Object> getInquiryContent = new HashMap<String, Object>();

		InquiryToSellerVO inquiryToSellerVO = customerService.getInquiryContent(inquiry_no);
		int product_no = inquiryToSellerVO.getProduct_no();
		ProductVO productVO = productService.selectByProductNo(product_no);
		getInquiryContent.put("inquiryToSellerVO", inquiryToSellerVO);
		getInquiryContent.put("productVO", productVO);

		model.addAttribute("getInquiryContent", getInquiryContent);

		return "myPage/myPage_inquiryDetail";
	}

	// ???????????? ??????????????????
	@RequestMapping("readReplyContent.do")
	public String readReply(Model model, int inquiry_no) {
		HashMap<String, Object> getReplyContent = new HashMap<String, Object>();
		InquiryToSellerVO inquiryToSellerVO = customerService.getReplyContent(inquiry_no);
		int product_no = inquiryToSellerVO.getProduct_no();
		ProductVO productVO = productService.selectByProductNo(product_no);
		getReplyContent.put("inquiryToSellerVO", inquiryToSellerVO);
		getReplyContent.put("productVO", productVO);

		model.addAttribute("getInquiryContent", getReplyContent);

		return "myPage/myPage_inquiryDetail";
	}

	// ???????????? ?????????????????
	@RequestMapping("inquiryDelete.do")
	public String inquiryDelete(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CustomerVO customerVO = (CustomerVO) session.getAttribute("sessionCustomer");

		InquiryToSellerVO inquiryToSellerVO = new InquiryToSellerVO();
		System.out.println(request.getParameter("inquiry_no"));
		inquiryToSellerVO.setInquiry_no(Integer.parseInt(request.getParameter("inquiry_no")));

		if (customerVO != null) {
			customerService.deleteInquiry(inquiryToSellerVO);
		} else {
			return "redirect:./login";
		}

		return "redirect:./inquiryList.do";
	}

	// ???????? ???????????? ????????????????????????????
	@RequestMapping("Inquiry.do")
	public String InquiryInsert() {

		return "product/product_note";
	}

	// ???????? ???????????? ?????????? ????????????????????
	@RequestMapping("InquiryProcess.do")
	public String InquiryInsert(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CustomerVO customerVO = (CustomerVO) session.getAttribute("sessionCustomer");
		int customer_no = customerVO.getCustomer_no();

		System.out.println(customer_no);
		System.out.println(request.getParameter("inquiry_title"));
		System.out.println(request.getParameter("inquiry_content"));
		System.out.println(request.getParameter("product_no"));
		System.out.println(request.getParameter("seller_no"));

		InquiryToSellerVO inquiryToSellerVO = new InquiryToSellerVO();
		inquiryToSellerVO.setCustomer_no(customer_no);
		inquiryToSellerVO.setInquiry_title(request.getParameter("inquiry_title"));
		inquiryToSellerVO.setInquiry_content(request.getParameter("inquiry_content"));
		inquiryToSellerVO.setProduct_no((Integer.parseInt(request.getParameter("product_no"))));
		inquiryToSellerVO.setSeller_no((Integer.parseInt(request.getParameter("seller_no"))));

		customerService.insertInquiry(inquiryToSellerVO);

		return "product/product_inquirySendSuccess";
	}
	
	
	//?????????
	//???????????? ??????
	@RequestMapping(value = "/myPage_orderList", method = RequestMethod.GET)
	public ModelAndView orderList(OrderVO orderVO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView("redirect:./login");
		if(session.getAttribute("sessionCustomer") != null) {
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			int page = 1;
			if(request.getParameter("page") !=null) page= Integer.parseInt(request.getParameter("page"));
			if(request.getParameter("fromDate") !=null && request.getParameter("fromDate") != "") paraMap.put("fromDate", request.getParameter("fromDate"));
			if(request.getParameter("toDate") !=null && request.getParameter("toDate") != "") paraMap.put("toDate", request.getParameter("toDate"));
			paraMap.put("page", (page-1)*10);
			paraMap.put("customer_no",session.getAttribute("sessionCustomer_no"));
			if(request.getParameter("fromDate") !=null && request.getParameter("toDate") !=null 
					&& request.getParameter("fromDate") != "" && request.getParameter("toDate") != "") {				
				int totalCount = customerService.orderListCount_date(paraMap);
				mv.addObject("totalCount", totalCount);
			} else {
				int totalCount = customerService.orderListCount(Integer.parseInt(paraMap.get("customer_no").toString()));
				mv.addObject("totalCount", totalCount);
			}
			List<OrderVO> orderList = customerService.orderList(paraMap);
			mv.addObject("orderList", orderList);
			if(request.getParameter("fromDate") !=null) mv.addObject("fromDate", request.getParameter("fromDate"));
			if(request.getParameter("toDate") !=null) mv.addObject("toDate", request.getParameter("toDate"));
			int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
			int myCouponCount = customerService.myCouponCount(customer_walletNo);
			int myCash = customerService.myCash(customer_walletNo);
			int myMileage = customerService.myMileage(customer_walletNo);
			CustomerImgVO customerImg = customerService.customerDetail_img(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
			//????????????
			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("ip", ipGet.getUserIP(request));
			log.put("link", "???????????????-?????? ?????? ?????????");
			if(session.getAttribute("sessionCustomer_nick") != null) {
				log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
			}
			adminService.accessLog(log);
			mv.addObject("customerImg", customerImg);
			mv.addObject("page", page);
			mv.addObject("myCouponCount", myCouponCount);
			mv.addObject("myCash", myCash);
			mv.addObject("myMileage", myMileage);
			mv.setViewName("myPage/myPage_orderList");
		}
		mv.addObject("message", "?????????????????????");
		return mv;
	}
	//???????????????
    @RequestMapping("myPage_review")
    public String myPage_review(HttpSession session,HttpServletRequest request, Model model) {
    	int page = 1;
		if(request.getParameter("page") !=null) page= Integer.parseInt(request.getParameter("page"));
    	CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
       int customer_no = sessionCustomer.getCustomer_no();
       ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
       HashMap<String, Object> paging = new HashMap<String, Object>();
       paging.put("page", (page-1)*10);
       paging.put("customer_no", customer_no);
       ArrayList<ReviewVO> reviewList = customerService.selectReviewList(paging);
       int totalCount = customerService.selectReviewCount(customer_no);
       for(ReviewVO reviewVO : reviewList) {
          HashMap<String, Object> map = new HashMap<String, Object>();
          int product_no = reviewVO.getProduct_no();
          ProductVO productVO = productService.selectByProductNo(product_no);
          
          map.put("productVO", productVO);
          map.put("reviewVO", reviewVO);
          result.add(map);
       }
       int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
		int myCouponCount = customerService.myCouponCount(customer_walletNo);
		int myCash = customerService.myCash(customer_walletNo);
		int myMileage = customerService.myMileage(customer_walletNo);
		CustomerImgVO customerImg = customerService.customerDetail_img(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
		
     //????????????
     HashMap<String, Object> log = new HashMap<String, Object>();
     log.put("ip", ipGet.getUserIP(request));
     log.put("link", "???????????????-????????????");
     if(session.getAttribute("sessionCustomer_nick") != null) {
     	log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
     }
     		adminService.accessLog(log);
       model.addAttribute("data", result);
       model.addAttribute("customerImg", customerImg);
       model.addAttribute("page", page);
       model.addAttribute("myCouponCount", myCouponCount);
       model.addAttribute("myCash", myCash);
       model.addAttribute("myMileage", myMileage);
       model.addAttribute("totalCount", totalCount);
       return "myPage/myPage_review";
    }
		
		//????????????
		   @RequestMapping("myPage_likeList")
		   public String myPage_likeList(HttpSession session, HttpServletRequest request, Model model) {
		      int page = 1;
		      if(request.getParameter("page") !=null) page= Integer.parseInt(request.getParameter("page"));
		       CustomerVO sessionCustomer = (CustomerVO)session.getAttribute("sessionCustomer");
		        int customer_no = sessionCustomer.getCustomer_no();
		        ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
		        ArrayList<LikeVO> likeList = customerService.likeList(customer_no,(page-1)*10);
		        for(LikeVO likeVO :likeList) {
		          HashMap<String, Object> map = new HashMap<String, Object>();
		          int product_no = likeVO.getProduct_no();
		          System.out.println("pno:"+product_no);
		          ProductVO productVO = productService.selectByProductNo(product_no);
		          int seller_no = productVO.getSeller_no();
		          System.out.println("seller_no:"+seller_no);
		          SellerVO sellerVO = sellerService.selectSellerInfoBySno(seller_no);
		          int customerNo = sellerVO.getCustomer_no();
		          System.out.println("customerNo:"+customerNo);
		          CustomerImgVO customerImgVO = customerService.selectImgByCustomerNo(customerNo);
		          
		          map.put("customerImgVO", customerImgVO);
		          map.put("sellerVO", sellerVO);
		          map.put("productVO", productVO);
		          map.put("likeVO", likeVO);
		          result.add(map);
		       }
		       int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
		       int myCouponCount = customerService.myCouponCount(customer_walletNo);
		       int myCash = customerService.myCash(customer_walletNo);
		       int myMileage = customerService.myMileage(customer_walletNo);
		       CustomerImgVO customerImg = customerService.customerDetail_img(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
		       int likeListCount = customerService.likeListCount(customer_no);
		       //????????????
		      HashMap<String, Object> log = new HashMap<String, Object>();
		      log.put("ip", ipGet.getUserIP(request));
		      log.put("link", "???????????????-??? ??????");
		      if(session.getAttribute("sessionCustomer_nick") != null) {
		         log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
		      }
		      adminService.accessLog(log);
		      model.addAttribute("data", result);
		      model.addAttribute("customerImg", customerImg);
		       model.addAttribute("page", page);
		       model.addAttribute("myCouponCount", myCouponCount);
		       model.addAttribute("myCash", myCash);
		       model.addAttribute("myMileage", myMileage);
		       model.addAttribute("page", page);	
		       model.addAttribute("totalCount", likeListCount);
		       return "myPage/myPage_likeList";
		   }
		   
		// ???????????? ?????? ??????
		@RequestMapping(value = "/myPage_chargeMileageList", method = RequestMethod.GET)
		public ModelAndView chargeList(MileageVO mileageVO, HttpServletRequest request) {
			HttpSession session = request.getSession();
			ModelAndView mv = new ModelAndView("redirect:./login");
			if(session.getAttribute("sessionCustomer") != null) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				int page = 1;
				if(request.getParameter("page") !=null) page= Integer.parseInt(request.getParameter("page"));
				if(request.getParameter("fromDate") !=null && request.getParameter("fromDate") != "") map.put("fromDate", request.getParameter("fromDate"));
				if(request.getParameter("toDate") !=null && request.getParameter("toDate") != "") map.put("toDate", request.getParameter("toDate"));
				mv.setViewName("myPage/myPage_chargeMileageList");
				int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
				map.put("customer_walletNo", customer_walletNo);
				map.put("page", (page-1)*10);
				ArrayList<MileageVO> mileageList = customerService.mileageList(map);
				int myCouponCount = customerService.myCouponCount(customer_walletNo);
				int myCash = customerService.myCash(customer_walletNo);
				int myMileage = customerService.myMileage(customer_walletNo);
				CustomerImgVO customerImg = customerService.customerDetail_img(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
				if(request.getParameter("fromDate") !=null && request.getParameter("toDate") !=null 
						&& request.getParameter("fromDate") != "" && request.getParameter("toDate") != "") {				
					int totalCount = customerService.mileageCount_date(map);
					mv.addObject("totalCount", totalCount);
				} else {
					int totalCount = customerService.mileageCount(customer_walletNo);
					mv.addObject("totalCount", totalCount);
				}
				//????????????
				HashMap<String, Object> log = new HashMap<String, Object>();
				log.put("ip", ipGet.getUserIP(request));
				log.put("link", "???????????????-???????????? ?????? ??????");
				if(session.getAttribute("sessionCustomer_nick") != null) {
					log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
				}
				adminService.accessLog(log);
				mv.addObject("customerImg", customerImg);
				mv.addObject("myCash", myCash);
				mv.addObject("myMileage", myMileage);
				mv.addObject("myCouponCount", myCouponCount);
				mv.addObject("mileageList", mileageList);
				mv.addObject("page", page);
			}
			mv.addObject("message", "?????????????????????");
			return mv;
		}

		//?????? ???????????????
		@RequestMapping("myPage_chargeCash")
		public String myPage_chargeCash(HttpSession session,HttpServletRequest request, Model model) {
			if(session.getAttribute("sessionCustomer") != null) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				CustomerVO customerSession = (CustomerVO)session.getAttribute("sessionCustomer");
				int customer_no = customerSession.getCustomer_no();
				CustomerWalletVO customerWalletVO = customerService.selectMyWalletByCustomerNo(customer_no);
				int customer_wallet_no = customerWalletVO.getCustomer_wallet_no();
				int myCouponCount = customerService.myCouponCount(customer_wallet_no);
				int myCash = customerService.myCash(customer_wallet_no);
				int myMileage = customerService.myMileage(customer_wallet_no);
				CustomerImgVO customerImg = customerService.customerDetail_img(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
				model.addAttribute("customerImg", customerImg);
				model.addAttribute("myCash", myCash);
				model.addAttribute("myMileage", myMileage);
				model.addAttribute("myCouponCount", myCouponCount);
				CashVO cashVO = customerService.selectMyCashByWalletNo(customer_wallet_no);
				map.put("customerWalletVO", customerWalletVO);
				map.put("cashVO", cashVO);
				model.addAttribute("myCouponCount", myCouponCount);
				model.addAttribute("data", map);
				//????????????
				HashMap<String, Object> log = new HashMap<String, Object>();
				log.put("ip", ipGet.getUserIP(request));
				log.put("link", "???????????????-?????? ??????");
				if(session.getAttribute("sessionCustomer_nick") != null) {
					log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
				}
				adminService.accessLog(log);
				return "myPage/myPage_chargeCash";
			}
			model.addAttribute("message", "?????????????????????");
			return "redirect:./login";
		}
		//?????? ?????? ????????????
		@RequestMapping("ChargeCashProcess.do")
		public String ChargeCashProcess(HttpSession session, HttpServletRequest request) {
			if(session.getAttribute("sessionCustomer") != null) {
				CustomerVO customerSession = (CustomerVO)session.getAttribute("sessionCustomer");
				int customer_no = customerSession.getCustomer_no();
				
				CustomerWalletVO customerWalletVO = customerService.selectMyWalletByCustomerNo(customer_no);
				int customer_wallet_no = customerWalletVO.getCustomer_wallet_no();
				
				int cash_charge = (Integer.parseInt(request.getParameter("ChargeCash")));
				System.out.println(cash_charge);
				int cash_amount = (Integer.parseInt(request.getParameter("cash_amount")));
				System.out.println(cash_amount);
				
				CashVO cashVO = new CashVO();
				customerService.applyCashCharge(cash_charge, customer_wallet_no, cashVO, cash_amount);
				
				return "myPage/myPage_chargeCash";
			}
			return "redirect:./";
		}
		//???????????? ??????
		@RequestMapping("myPage_chargeCashList")
		public String myPage_chargeCashList(HttpSession session, Model model,HttpServletRequest request) {
			if(session.getAttribute("sessionCustomer") != null) {
				HashMap<String, Object> count = new HashMap<String, Object>();
				int page = 1;
				if(request.getParameter("page") !=null)	page= Integer.parseInt(request.getParameter("page"));
				count.put("page", (page-1)*10);
				if(request.getParameter("fromDate") !=null && request.getParameter("fromDate") != "") count.put("fromDate", request.getParameter("fromDate"));
				if(request.getParameter("toDate") !=null && request.getParameter("toDate") != "") count.put("toDate", request.getParameter("toDate"));
				CustomerVO customerSession = (CustomerVO)session.getAttribute("sessionCustomer");
				int customer_no = customerSession.getCustomer_no();	
				CustomerWalletVO customerWalletVO = customerService.selectMyWalletByCustomerNo(customer_no);
				ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
				int customer_wallet_no = customerWalletVO.getCustomer_wallet_no();
				count.put("customer_wallet_no", customer_wallet_no);
				ArrayList<CashVO> cashVOList = customerService.selectMyCashListByWalletNo(count);
				if(request.getParameter("fromDate") !=null && request.getParameter("toDate") !=null 
						&& request.getParameter("fromDate") != "" && request.getParameter("toDate") != "") {				
					int totalCount = customerService.chargeCashCount_date(count);
					System.out.println(totalCount);
					model.addAttribute("totalCount", totalCount);
				} else {
					int totalCount = customerService.chargeCashCount(customer_wallet_no);
					model.addAttribute("totalCount", totalCount);
				}
				int myCouponCount = customerService.myCouponCount(customer_wallet_no);
				int myCash = customerService.myCash(customer_wallet_no);
				int myMileage = customerService.myMileage(customer_wallet_no);
				CustomerImgVO customerImg = customerService.customerDetail_img(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
				model.addAttribute("customerImg", customerImg);
				model.addAttribute("myCash", myCash);
				model.addAttribute("myMileage", myMileage);
				model.addAttribute("myCouponCount", myCouponCount);
				model.addAttribute("page", page);
				for(CashVO cashVO : cashVOList) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("cashVO", cashVO);
					result.add(map);
				}
				model.addAttribute("data", result);
				//????????????
				HashMap<String, Object> log = new HashMap<String, Object>();
				log.put("ip", ipGet.getUserIP(request));
				log.put("link", "???????????????-?????? ?????? ??????");
				if(session.getAttribute("sessionCustomer_nick") != null) {
					log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
				}
				adminService.accessLog(log);
				return"myPage/myPage_chargeCashList";	
			}
			model.addAttribute("message", "?????????????????????");
			return "redirect:./";
		}
		@GetMapping(value = "/payment")
		public ModelAndView payment(HttpServletRequest request) {
			ModelAndView mv= new ModelAndView("product/product_payment");
			HttpSession session = request.getSession();
			if((session.getAttribute("sessionAdmin") != null||session.getAttribute("sessionCustomer") != null )&& request.getParameter("bno") != null) {
				//?????? ?????????
				ProductVO productdetail = sellerService.productDetail(Integer.parseInt(request.getParameter("bno")));
				// ????????? ??????
				CustomerImgVO customerDetail_img = customerService.customerDetail_img(productdetail.getCustomer_no());
				// ??????(?????????) ?????????
				String customer_nick = sellerService.customer_nick(productdetail.getCustomer_no());
				// ????????? ?????????
				SellerVO sellerDetail = sellerService.sellerDetail(productdetail.getCustomer_no());
				// ?????? ?????? ??????(?????????)
				int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
				//?????? ?????????(?????????)
				ArrayList<HashMap<String, Object>> myCoupon = customerService.myCouponUseList(customer_walletNo);
				// ?????? ??? ????????????(?????????)
				int mileage = customerService.mileageAmount(customer_walletNo);
				if(mileage+"" == null || mileage+"" == "") {
					mileage = 0;
				}
				// ?????? ?????? ?????? ??????
				int cash_amount = customerService.cashAmount(customer_walletNo);
				if(request.getParameter("coupon") != null && request.getParameter("coupon") != "") {
					HashMap<String, Object> myCouponDetail = customerService.myCouponDetail(Integer.parseInt(request.getParameter("coupon")));
					mv.addObject("myCouponDetail", myCouponDetail);
				}
				mv.addObject("productDetail", productdetail);
				mv.addObject("customerDetail_img", customerDetail_img);
				mv.addObject("customer_Nick", customer_nick);
				mv.addObject("sellerDetail", sellerDetail);
				mv.addObject("myCoupon", myCoupon);
				mv.addObject("mileage", mileage);
				mv.addObject("cash_amount", cash_amount);
				//????????????
				HashMap<String, Object> log = new HashMap<String, Object>();
				log.put("ip", ipGet.getUserIP(request));
				log.put("link", "???????????????");
				if(session.getAttribute("sessionCustomer_nick") != null) {
					log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
				}
				adminService.accessLog(log);
				return mv;
			} else {
				//????????? ????????????
				mv.setViewName("redirect:/login?login=error");
				return mv;
			}
			
		}
		
		//?????? ??????
		@PostMapping(value = "/payment")
		public ModelAndView orderPayment(HttpServletRequest request) {
			HttpSession session = request.getSession();
			ModelAndView mv = new ModelAndView("redirect:/paymentCompleted");		
			if((session.getAttribute("sessionCustomer") != null || session.getAttribute("sessionAdmin") != null )
					&& request.getParameter("orderPrice") != null && request.getParameter("totalDiscountPrice") != null 
					&& request.getParameter("finallyPrice") != null && request.getParameter("payment_item") != null
					&& request.getParameter("bno") != null) {
				// ?????? ?????? ??????(?????????)
				int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
				if(request.getParameter("payment_item").equals("cash")) {
					// ?????? ?????? ?????? ??????
					int cash_amount = customerService.cashAmount(customer_walletNo);
					CashVO cash = new CashVO();
					cash.setCustomer_wallet_no(customer_walletNo);
					cash.setCash_amount((cash_amount-Integer.parseInt(request.getParameter("finallyPrice"))));
					cash.setCash_usage(Integer.parseInt(request.getParameter("finallyPrice")));
					if(cash_amount >= Integer.parseInt(request.getParameter("finallyPrice"))) {
						//?????? ?????? ??????
						customerService.useCash(cash);					
						HashMap<String, Object> map = new HashMap<String, Object>();
						//??????????????? ???????????? ??????
						if(request.getParameter("useMileage") != null && request.getParameter("useMileage") != "" &&
								request.getParameter("useMileage") != "0") {
							map.put("order_useMileage", request.getParameter("useMileage"));				
						} else {
							map.put("mileage_add", (int)(Integer.parseInt(request.getParameter("finallyPrice"))*0.05));
						}
						map.put("orderPrice", request.getParameter("orderPrice"));
						map.put("order_totalDiscountPrice", request.getParameter("totalDiscountPrice"));
						map.put("order_couponDiscountPrice", request.getParameter("couponDiscountPrice"));
						map.put("order_payType", request.getParameter("payment_item"));
						map.put("totalPrice", request.getParameter("finallyPrice"));
						map.put("product_no", request.getParameter("bno"));
						map.put("customer_no", session.getAttribute("sessionCustomer_no"));
						int seller_no = customerService.productSellerNo(Integer.parseInt(request.getParameter("bno")));
						map.put("seller_no", seller_no);
						//?????? ????????? ???????????? 
						if(request.getParameter("couponSelect") != null && request.getParameter("couponSelect") != "" &&
								request.getParameter("couponSelect") != "0") {
							HashMap<String, Object> myCouponDetail = customerService.myCouponDetail(Integer.parseInt(request.getParameter("couponSelect")));
							map.put("coupon_no", myCouponDetail.get("coupon_no"));
						}
						// ????????? ????????? ??????
						if(request.getParameter("payment_item").equals("cash")) {
							map.put("order_status", 'Y');
						}
						// ?????? ??????
						customerService.productPayment(map);
						if(request.getParameter("couponSelect") != null && request.getParameter("couponSelect") != "" &&
								request.getParameter("couponSelect") != "0") {
							//???????????? ?????? ??????
							customerService.myCoupon_status(Integer.parseInt(request.getParameter("couponSelect")));
						}
						
						// ?????? ???????????? ??????
						int mileage_amount = customerService.mileageAmount(customer_walletNo);
						MileageVO mileage = new MileageVO();
						mileage.setCustomer_wallet_no(customer_walletNo);
						if(request.getParameter("useMileage") != null && request.getParameter("useMileage") != "" &&
								request.getParameter("useMileage") != "0") {	
							mileage.setMileage_amount((mileage_amount-Integer.parseInt(request.getParameter("useMileage"))));
							mileage.setMileage_usage(Integer.parseInt(request.getParameter("useMileage")));
						} else {
							mileage.setMileage_amount((mileage_amount+Integer.parseInt(map.get("mileage_add").toString())));
							mileage.setMileage_add(Integer.parseInt(map.get("mileage_add").toString()));
						}
						//???????????? ????????????
						customerService.useMileage(mileage);
						session.setAttribute("bno",request.getParameter("bno"));
						
						//????????? ????????????
						map.put("seller_no", seller_no);
						sellerService.sellerIncome(map);
					} else {
						// ?????? ????????? ?????? ?????? ?????? ?????????
						mv.setViewName("redirect:/myPage_chargeCash?error=nomoney");
					}
				}
			} else {
				// ?????????,????????????, ??????, ????????????, ????????????, bno??? ??????????????????
				mv.setViewName("redirect:/login?error=login");
			}
			return mv;
		}
		
		@GetMapping(value = "/paymentCompleted")
		public ModelAndView paymentCompleted(HttpServletRequest request) {
			HttpSession session = request.getSession();
			ModelAndView mv = new ModelAndView("product/product_paymentCompleted");
			if((session.getAttribute("sessionAdmin") != null||session.getAttribute("sessionCustomer") != null)) {
				//?????? ?????????
				ProductVO productdetail = sellerService.productDetail(Integer.parseInt(session.getAttribute("bno").toString()));
				// ???????????? ?????????
				OrderVO orderDetail = customerService.orderDetail(Integer.parseInt(session.getAttribute("bno").toString()));
				// ?????? ????????????
				String mainCate = customerService.mainCate(productdetail.getMainCate_no());
				String subCate = customerService.subCate(productdetail.getSubCate_no());
				// ?????? ?????? ??????(?????????)
				int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
				// ???????????? ??????
				int mileage_add = customerService.mileage_add(customer_walletNo);
				mv.addObject("productDetail", productdetail);
				mv.addObject("customer_nick", session.getAttribute("sessionCustomer_nick"));
				mv.addObject("customer_email", session.getAttribute("sessionCustomer_email"));
				mv.addObject("orderDetail", orderDetail);
				mv.addObject("mainCate", mainCate);
				mv.addObject("subCate", subCate);
				mv.addObject("mileage_add", mileage_add);
			} else {
				mv.addObject("message", "?????????????????????");
				mv.setViewName("redirect:./login");
			}
			
			return mv;
		}
		
		//?????? ??????
		@GetMapping(value = "/myPage_couponList")
		public ModelAndView myPage_couponList(HttpServletRequest request) {
			HttpSession session = request.getSession();
			ModelAndView mv = new ModelAndView("redirect:./login");
			if(session.getAttribute("sessionCustomer") != null) {
				mv.setViewName("myPage/myPage_couponList");
				// ?????? ?????? ??????
				int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
				int page=1;
				if(request.getParameter("page")!=null) page= Integer.parseInt(request.getParameter("page"));
				ArrayList<HashMap<String, Object>> myCoupon = customerService.myCouponList(customer_walletNo,(page-1)*10);
				int MyCouponTotalList = customerService.MyCouponTotalList(customer_walletNo);
				int myCouponCount = customerService.myCouponCount(customer_walletNo);
				int myCash = customerService.myCash(customer_walletNo);
				int myMileage = customerService.myMileage(customer_walletNo);
				CustomerImgVO customerImg = customerService.customerDetail_img(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
				String customer_nick = session.getAttribute("sessionCustomer_nick").toString();
				//????????????
				HashMap<String, Object> log = new HashMap<String, Object>();
				log.put("ip", ipGet.getUserIP(request));
				log.put("link", "???????????????-?????????");
				if(session.getAttribute("sessionCustomer_nick") != null) {
					log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
				}
				adminService.accessLog(log);
				mv.addObject("customerImg", customerImg);
				mv.addObject("customer_nick", customer_nick);
				mv.addObject("myCash", myCash);
				mv.addObject("myMileage", myMileage);
				mv.addObject("myCouponCount", myCouponCount);
				mv.addObject("myCoupon", myCoupon);
				mv.addObject("page", page);
				mv.addObject("totalCount", MyCouponTotalList);
			}
			mv.addObject("message", "?????????????????????");
			return mv;
		}
		
		// ?????? ??????
		@GetMapping(value = "/myPage_useCoupon")
		public String myPage_useCoupon(HttpServletRequest request) {
			HttpSession session = request.getSession();
			if(session.getAttribute("sessionCustomer") != null) {
				//????????????
				HashMap<String, Object> log = new HashMap<String, Object>();
				log.put("ip", ipGet.getUserIP(request));
				log.put("link", "???????????????-???????????????");
				if(session.getAttribute("sessionCustomer_nick") != null) {
					log.put("customer_nick", session.getAttribute("sessionCustomer_nick"));
				}
				adminService.accessLog(log);
				return "myPage/myPage_useCoupon";			
			}
			return "redirect:./login";
		}
		
		// ?????? ??? ????????????
		@PostMapping(value = "/ajaxCoupon", produces= "text/plain;charset=utf-8")
		public @ResponseBody String useCouponIssuance(HttpServletRequest request) {
			HttpSession session = request.getSession();
			StringBuffer sb = new StringBuffer();
			CouponVO coupon = customerService.registeredCoupon(request.getParameter("coupon_code"));
			sb.append("{\"coupon_name\" : \""+coupon.getCoupon_name()+"\"");
			sb.append(",\"coupon_discountPercent\":"+coupon.getCoupon_discountPercent());
			sb.append(",\"coupon_discountPrice\":"+coupon.getCoupon_discountPrice());
			sb.append(",\"coupon_no\":"+coupon.getCoupon_no());
			sb.append("}");
			return sb.toString();
		}
		// ?????? ??????
		@PostMapping(value = "/registeredCoupon")
		public String registeredCoupon(HttpServletRequest request) {
			HttpSession session = request.getSession();
			if(session.getAttribute("sessionCustomer") != null && request.getParameter("coupon_no") != null) {
				int customer_walletNo = customerService.customer_walletNo(Integer.parseInt(session.getAttribute("sessionCustomer_no").toString()));
				HashMap<String, Object> coupon =  new HashMap<String, Object>();
				coupon.put("coupon_no", request.getParameter("coupon_no"));
				coupon.put("customer_wallet_no", customer_walletNo);
				customerService.registerCoupon(coupon);
			}
			
			return "myPage/myPage_useCoupon";
		}
		
		//????????? ????????????
	      @ResponseBody
	      @RequestMapping(value= "/emailChk.do", method = RequestMethod.POST)
	      public int emailChk(HttpServletRequest request) {
	         
	         String customer_email = request.getParameter("customer_email");
	         System.out.println(customer_email);
	         
	         int result = customerService.emailChk(customer_email);
	         System.out.println(result);

	         return result;
	      }
	      
	    //?????????
	      @ResponseBody
	      @RequestMapping(value = "ajaxLike", method = RequestMethod.POST)
	      public String likeCheck(@RequestBody Map<String, String> map, HttpServletRequest request) throws Exception {
	         HttpSession session = request.getSession();
	         String bno =  map.get("bno");
	         String customer_no = map.get("customer_no");
	         String like_check = map.get("like_check");
	         Integer new_like_check = 0;
	         if ("0".equals(like_check)) {
	            customerService.like_check(map);
	            new_like_check = customerService.getLikedCheck(map);
	         }else if ("1".equals(like_check)) {
	            customerService.like_checkCancel(map);
	            new_like_check = customerService.getLikedCheck(map);
	         }
	         Map new_res = new HashMap<String, String >();
	         new_res.put("like_check", new_like_check+"");
	         new_res.put("bno", bno);
	         new_res.put("customer_no", customer_no);
	         ObjectMapper om = new ObjectMapper();
	         String json = om.writeValueAsString(new_res);
	         System.out.println(json);
	         return json;
	      }
	      

}