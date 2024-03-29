import textwrap
from fpdf import FPDF
import sys

def text_to_pdf(text, filename):
	a4_width_mm = 210
	pt_to_mm = 0.35
	fontsize_pt = 14
	fontsize_normal = 10
	fontsize_mm = fontsize_pt * pt_to_mm
	margin_bottom_mm = 10
	character_width_mm = 7 * pt_to_mm
	width_text = a4_width_mm / character_width_mm

	pdf = FPDF(orientation='P', unit='mm', format='A4')
	pdf.set_auto_page_break(True, margin=margin_bottom_mm)
	pdf.add_page()
	pdf.set_font(family='Courier', size=fontsize_pt)
	splitted = text.split('\n')
	i = 0

	for line in splitted:
		print(line)
		lines = textwrap.wrap(line, width_text)

		if len(lines) == 0:
			pdf.ln()

		for wrap in lines:
			pdf.cell(0, fontsize_mm, wrap, ln=1)

		if i == 0:
			pdf.set_font_size(fontsize_normal)

	pdf.output(filename, 'F')


name = sys.argv[1]
input_filename = name+'.txt'
output_filename = name+'.pdf'
file = open(input_filename)
text = file.read()
file.close()
text_to_pdf(text, output_filename)
