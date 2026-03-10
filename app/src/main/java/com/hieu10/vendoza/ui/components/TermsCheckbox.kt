package com.hieu10.vendoza.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.vendoza.R
import com.hieu10.vendoza.ui.theme.VendozaTheme

@Composable
fun TermsCheckbox(
    checked: Boolean,
    termsError: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    val terms = stringResource(id = R.string.txt_btn_terms)
    val privacy = stringResource(id = R.string.txt_btn_privacy)
    
    val sentence = stringResource(
        id = R.string.accept_terms,
        terms,
        privacy
    )

    val annotatedText = buildAnnotatedString {
        append(sentence)

        val termsStart = sentence.indexOf(terms)
        val termsEnd = termsStart + terms.length

        val privacyStart = sentence.indexOf(privacy)
        val privacyEnd = privacyStart + privacy.length

        addLink(
            LinkAnnotation.Clickable(
                tag = "terms",
                styles = TextLinkStyles(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            ) {
                onTermsClick()
            },
            termsStart,
            termsEnd
        )

        addLink(
            LinkAnnotation.Clickable(
                tag = "privacy",
                styles = TextLinkStyles(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            ) {
                onPrivacyClick()
            },
            privacyStart,
            privacyEnd
        )
    }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = if (termsError) {
                    CheckboxDefaults.colors(
                        uncheckedColor = MaterialTheme.colorScheme.error
                    )
                } else CheckboxDefaults.colors()
            )

            Text(
                text = annotatedText,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (termsError) {
            Text(
                text = stringResource(id = R.string.error_terms),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 48.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCheckboxLight() {
    VendozaTheme(darkTheme = false) {
        TermsCheckbox(
            checked = true,
            termsError = false,
            onCheckedChange = {},
            onTermsClick = {},
            onPrivacyClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCheckboxDark() {
    VendozaTheme(darkTheme = true) {
        TermsCheckbox(
            checked = true,
            termsError = false,
            onCheckedChange = {},
            onTermsClick = {},
            onPrivacyClick = {}
        )
    }
}