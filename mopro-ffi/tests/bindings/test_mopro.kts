import uniffi.mopro.*

var wasmPath = "../mopro-core/examples/circom/multiplier2/target/multiplier2_js/multiplier2.wasm"
var r1csPath = "../mopro-core/examples/circom/multiplier2/target/multiplier2.r1cs"

try {
    // Setup
    var moproCircom = MoproCircom()
    var setupResult = moproCircom.setup(wasmPath, r1csPath)
    assert(setupResult.provingKey.size > 0) { "Proving key should not be empty" }

    // Prepare inputs
    val inputs = mutableMapOf<String, List<String>>()
    inputs["a"] = listOf("3")
    inputs["b"] = listOf("5")

    // Generate proof
    var generateProofResult = moproCircom.generateProof(inputs)
    assert(generateProofResult.proof.size > 0) { "Proof is empty" }

    // Verify proof
    var isValid = moproCircom.verifyProof(generateProofResult.proof, generateProofResult.inputs)
    assert(isValid) { "Proof is invalid" }

    // Convert proof to Ethereum compatible proof
    var convertProofResult = toEthereumProof(generateProofResult.proof)
    var convertInputsResult = toEthereumInputs(generateProofResult.inputs)
    assert(convertProofResult.a.x.isNotEmpty()) { "Proof is empty" }
    assert(convertInputsResult.size > 0) { "Inputs are empty" }


} catch (e: Exception) {
    println(e)
}
