package miage.abdelali.app.services;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import java.awt.Color;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import com.lowagie.text.Element;
import miage.abdelali.app.dto.DevisRequestDto;
import miage.abdelali.app.dto.DevisResponseDto;

@Service
public class PdfService {

	public byte[] buildDevisPdf(String nomAffiche, DevisRequestDto req, DevisResponseDto res) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			Document doc = new Document(PageSize.A4, 36, 36, 48, 36);
			PdfWriter.getInstance(doc, baos);
			doc.open();

			Font h1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
			Font h2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
			Font body = FontFactory.getFont(FontFactory.HELVETICA, 10);
			Font small = FontFactory.getFont(FontFactory.HELVETICA, 9);
			Font keyF = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

			PdfPTable header = new PdfPTable(2);
			header.setWidthPercentage(100f);
			header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

			Image leftLogo = loadLogo("logos/selfassurance.png", 120, 60);
			Image rightLogo = loadLogo("logos/miage.png", 120, 60);

			PdfPCell cLeft = (leftLogo != null) ? new PdfPCell(leftLogo, false) : new PdfPCell(new Phrase(" "));
			cLeft.setHorizontalAlignment(Element.ALIGN_LEFT);
			cLeft.setBorder(Rectangle.NO_BORDER);
			cLeft.setPadding(0);

			PdfPCell cRight = (rightLogo != null) ? new PdfPCell(rightLogo, false) : new PdfPCell(new Phrase(" "));
			cRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cRight.setBorder(Rectangle.NO_BORDER);
			cRight.setPadding(0);

			header.addCell(cLeft);
			header.addCell(cRight);
			doc.add(header);

			PdfPTable sep = new PdfPTable(1);
			sep.setWidthPercentage(100f);
			PdfPCell sepCell = new PdfPCell(new Phrase(" "));
			sepCell.setBorder(Rectangle.BOTTOM);
			sepCell.setBorderWidthBottom(0.5f);
			sepCell.setBorderColor(new Color(230, 230, 230));
			sepCell.setPadding(0f);
			sep.addCell(sepCell);
			doc.add(sep);

			doc.add(new Paragraph("Devis d’assurance", h1));
			String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			String sousTitre = (nomAffiche != null && !nomAffiche.isBlank() ? nomAffiche + " • " : "") + "Émis le "
					+ now;
			Paragraph meta = new Paragraph(sousTitre, small);
			meta.setSpacingAfter(12f);
			doc.add(meta);

			String currency = res.getCurrency() != null ? res.getCurrency() : "EUR";
			String primeTTC = res.getPrimeTTC() != null ? res.getPrimeTTC().toString() : "-";

			PdfPTable synth = new PdfPTable(1);
			synth.setWidthPercentage(100f);
			PdfPCell synthCell = new PdfPCell(new Phrase("Prime TTC estimée : " + primeTTC + " " + currency, h2));
			synthCell.setBackgroundColor(new Color(246, 247, 255));
			synthCell.setBorderColor(new Color(230, 230, 235));
			synthCell.setPadding(10f);
			synth.addCell(synthCell);
			doc.add(synth);

			Paragraph pv = new Paragraph("Version tarifaire : " + nz(res.getVersionTarif()), small);
			pv.setSpacingAfter(10f);
			doc.add(pv);

			doc.add(new Paragraph("Détails de l’assuré", h2));
			PdfPTable tAssure = new PdfPTable(new float[] { 1.3f, 2.2f });
			tAssure.setWidthPercentage(100f);
			tAssure.getDefaultCell().setBorder(Rectangle.NO_BORDER);

			addKV(tAssure, "Date de naissance", nz(req.getBirthDate()), keyF, body);
			addKV(tAssure, "Fumeur", nz(req.getSmoker()), keyF, body);
			addKV(tAssure, "Statut professionnel", req.getStatutPro() != null ? req.getStatutPro().name() : "-", keyF,
					body);
			addKV(tAssure, "Assuré actuel", String.valueOf(Boolean.TRUE.equals(req.getAssureActuel())), keyF, body);
			addKV(tAssure, "Bénéficiaire", req.getBeneficiaire() != null ? req.getBeneficiaire().name() : "-", keyF,
					body);
			doc.add(tAssure);

			doc.add(new Paragraph(" "));

			doc.add(new Paragraph("Détails de l’offre", h2));
			PdfPTable tOffre = new PdfPTable(new float[] { 1.3f, 2.2f });
			tOffre.setWidthPercentage(100f);
			tOffre.getDefaultCell().setBorder(Rectangle.NO_BORDER);

			addKV(tOffre, "Produit", nz(req.getProductCode()), keyF, body);
			addKV(tOffre, "Version", nz(req.getVersionCode()), keyF, body);
			addKV(tOffre, "Couverture", req.getCouverture() != null ? req.getCouverture().name() : "-", keyF, body);
			addKV(tOffre, "Niveau protection",
					req.getNiveauProtection() != null ? req.getNiveauProtection().name() : "-", keyF, body);
			addKV(tOffre, "Capital décès", req.getCapital() != null ? req.getCapital().toString() + " EUR" : "-", keyF,
					body);
			addKV(tOffre, "IJ / Franchise",
					(req.getIndemniteJournaliere() != null ? req.getIndemniteJournaliere() + " €/j" : "-") + " / "
							+ (req.getFranchiseJours() != null ? req.getFranchiseJours() + " j" : "-"),
					keyF, body);
			doc.add(tOffre);

			if (res.getPrimeNette() != null || res.getFrais() != null || res.getTaxe() != null) {
				Paragraph hd = new Paragraph("Décomposition", h2);
				hd.setSpacingBefore(10f);
				doc.add(hd);

				PdfPTable tDec = new PdfPTable(new float[] { 1.3f, 2.2f });
				tDec.setWidthPercentage(100f);
				tDec.getDefaultCell().setBorder(Rectangle.NO_BORDER);

				addKV(tDec, "Prime nette",
						res.getPrimeNette() != null ? res.getPrimeNette().toString() + " " + currency : "-", keyF,
						body);
				addKV(tDec, "Frais", res.getFrais() != null ? res.getFrais().toString() + " " + currency : "-", keyF,
						body);
				addKV(tDec, "Taxe", res.getTaxe() != null ? res.getTaxe().toString() + " " + currency : "-", keyF,
						body);
				doc.add(tDec);
			}

			Paragraph foot = new Paragraph("Document généré automatiquement — non contractuel", small);
			foot.setSpacingBefore(16f);
			doc.add(foot);

			doc.close();
			return baos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("PDF generation error", e);
		}
	}

	private static void addKV(PdfPTable t, String key, String val, Font keyFont, Font valFont) {
		PdfPCell ck = new PdfPCell(new Phrase(key, keyFont));
		ck.setBorder(Rectangle.NO_BORDER);
		ck.setPadding(3f);
		PdfPCell cv = new PdfPCell(new Phrase(val == null ? "-" : val, valFont));
		cv.setBorder(Rectangle.NO_BORDER);
		cv.setPadding(3f);
		t.addCell(ck);
		t.addCell(cv);
	}

	private static Image loadLogo(String classpathLocation, float maxW, float maxH) {
		try (InputStream in = new ClassPathResource(classpathLocation).getInputStream()) {
			byte[] bytes = in.readAllBytes();
			Image img = Image.getInstance(bytes);
			img.scaleToFit(maxW, maxH);
			return img;
		} catch (Exception e) {
			return null;
		}
	}

	private static PdfPCell cell(String txt, boolean header) {
		Font f = header ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)
				: FontFactory.getFont(FontFactory.HELVETICA, 10);
		PdfPCell c = new PdfPCell(new Phrase(txt == null ? "-" : txt, f));
		c.setPadding(6f);
		return c;
	}

	private static String nz(String v) {
		return v == null ? "-" : v;
	}
}