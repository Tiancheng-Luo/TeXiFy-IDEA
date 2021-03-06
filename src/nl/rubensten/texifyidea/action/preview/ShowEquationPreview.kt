package nl.rubensten.texifyidea.action.preview

import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import nl.rubensten.texifyidea.TexifyIcons
import nl.rubensten.texifyidea.psi.LatexDisplayMath
import nl.rubensten.texifyidea.psi.LatexInlineMath
import nl.rubensten.texifyidea.ui.PreviewFormUpdater
import nl.rubensten.texifyidea.util.findOuterMathEnvironment
import nl.rubensten.texifyidea.util.inMathContext

/**
 * Show a preview of a LaTeX equation in a separate window.
 *
 * @author Sergei Izmailov
 */
class ShowEquationPreview : PreviewAction("Equation Preview", TexifyIcons.EQUATION_PREVIEW) {

    companion object {

        @JvmStatic
        val FORM_KEY = Key<PreviewFormUpdater>("updater")

        @JvmStatic
        val MATH_PREAMBLE = "\\usepackage{amsmath,amsthm,amssymb,amsfonts}"
    }

    override fun actionPerformed(file: VirtualFile, project: Project, textEditor: TextEditor) {
        val element: PsiElement = getElement(file, project, textEditor) ?: return

        val outerMathEnvironment = element.findOuterMathEnvironment() ?: return

        displayPreview(project, outerMathEnvironment, FORM_KEY) {
            preamble += MATH_PREAMBLE
        }
    }
}