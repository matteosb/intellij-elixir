// This is a generated file. Not intended for manual editing.
package org.elixir_lang.psi.impl;

import com.ericsson.otp.erlang.OtpErlangObject;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.elixir_lang.psi.*;
import org.jetbrains.annotations.NotNull;

public class ElixirInterpolatedWordsHeredocLineImpl extends ASTWrapperPsiElement implements ElixirInterpolatedWordsHeredocLine {

  public ElixirInterpolatedWordsHeredocLineImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ElixirVisitor) ((ElixirVisitor)visitor).visitInterpolatedWordsHeredocLine(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public ElixirHeredocLinePrefix getHeredocLinePrefix() {
    return findNotNullChildByClass(ElixirHeredocLinePrefix.class);
  }

  @Override
  @NotNull
  public ElixirInterpolatedWordsBody getInterpolatedWordsBody() {
    return findNotNullChildByClass(ElixirInterpolatedWordsBody.class);
  }

  public Body getBody() {
    return ElixirPsiImplUtil.getBody(this);
  }

  @NotNull
  public OtpErlangObject quote(Heredoc heredoc, int prefixLength) {
    return ElixirPsiImplUtil.quote(this, heredoc, prefixLength);
  }

}
