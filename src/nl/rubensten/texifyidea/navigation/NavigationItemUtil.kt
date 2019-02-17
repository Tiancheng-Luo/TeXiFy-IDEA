package nl.rubensten.texifyidea.navigation

import com.intellij.navigation.NavigationItem
import com.intellij.psi.PsiElement
import com.intellij.util.xml.model.gotosymbol.GoToSymbolProvider
import nl.rubensten.texifyidea.TexifyIcons
import nl.rubensten.texifyidea.psi.BibtexId
import nl.rubensten.texifyidea.psi.LatexCommands
import nl.rubensten.texifyidea.util.Magic
import nl.rubensten.texifyidea.util.forcedFirstRequiredParameterAsCommand
import nl.rubensten.texifyidea.util.requiredParameter

/**
 * @author Ruben Schellekens
 */
object NavigationItemUtil {

    @JvmStatic
    fun createLabelNavigationItem(psiElement: PsiElement): NavigationItem? {
        when (psiElement) {
            is LatexCommands -> return GoToSymbolProvider.BaseNavigationItem(psiElement,
                    psiElement.requiredParameter(0) ?: return null,
                    if (psiElement.name in Magic.Command.labels) TexifyIcons.DOT_LABEL else TexifyIcons.DOT_BIB
            )
            is BibtexId -> return GoToSymbolProvider.BaseNavigationItem(psiElement,
                    psiElement.name ?: return null,
                    TexifyIcons.DOT_BIB
            )
        }

        return null
    }

    @JvmStatic
    fun createCommandDefinitionNavigationItem(psiElement: LatexCommands): NavigationItem? {
        val defined = psiElement.forcedFirstRequiredParameterAsCommand() ?: return null
        return GoToSymbolProvider.BaseNavigationItem(psiElement, defined.name ?: "", TexifyIcons.DOT_COMMAND)
    }

    @JvmStatic
    fun createEnvironmentDefinitionNavigationItem(psiElement: LatexCommands): NavigationItem? {
        val environmentName = psiElement.requiredParameter(0) ?: return null
        return GoToSymbolProvider.BaseNavigationItem(psiElement, environmentName, TexifyIcons.DOT_ENVIRONMENT)
    }
}