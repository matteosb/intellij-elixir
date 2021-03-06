package org.elixir_lang.psi

import com.intellij.psi.ElementDescriptionLocation
import com.intellij.psi.ResolveState
import com.intellij.usageView.UsageViewTypeLocation
import org.elixir_lang.psi.call.Call
import org.elixir_lang.psi.call.name.Function.USE
import org.elixir_lang.psi.call.name.Module.KERNEL
import org.elixir_lang.psi.impl.call.finalArguments
import org.elixir_lang.psi.impl.maybeModularNameToModular
import org.elixir_lang.psi.scope.putVisitedElement

/**
 * A `use` call
 */
object Use {
    /**
     * Calls `function` on each call definition clause added to the scope from the `quote` block inside the `__using__`
     * macro called by `useCall` while `function` returns `true`.  Stops the first time `function` returns `false`.
     */
    fun callDefinitionClauseCallWhile(useCall: Call, resolveState: ResolveState, keepProcessing: (Call, ResolveState) -> Boolean): Boolean =
        modular(useCall)?.let { modularCall ->
            var accumulatedKeepProcessing = true

            for (definer in Using.definers(modularCall)) {
                val childResolveState = resolveState.putVisitedElement(definer)

                accumulatedKeepProcessing = Using.callDefinitionClauseCallWhile(
                        usingCall = definer,
                        useCall = useCall,
                        resolveState = childResolveState,
                        keepProcessing = keepProcessing
                )

                if (!accumulatedKeepProcessing) {
                    break
                }
            }

            accumulatedKeepProcessing
        } ?: true

    fun elementDescription(@Suppress("UNUSED_PARAMETER") call: Call, location: ElementDescriptionLocation): String? {
        var elementDescription: String? = null

        if (location === UsageViewTypeLocation.INSTANCE) {
            elementDescription = "use"
        }

        return elementDescription
    }

    /**
     * Whether `call` is a `use Module` or `use Module, opts` call.
     */
    @JvmStatic
    fun `is`(call: Call): Boolean =
        call.isCalling(KERNEL, USE, 1) || call.isCalling(KERNEL, USE, 2);

    /**
     * The modular that is used by `useCall`.
     *
     * @param useCall a [Call] where [is] is `true`.
     * @return `defmodule`, `defimpl`, or `defprotocol` used by `useCall`.  It can be `null` if Alias passed to
     *    `useCall` cannot be resolved.
     */
    fun modular(useCall: Call): Call? =
            useCall
                    .finalArguments()
                    ?.firstOrNull()
                    ?.maybeModularNameToModular(maxScope = useCall.parent, useCall = useCall)
}
