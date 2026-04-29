# Security Policy

Thank you for taking the time to report a potential security issue. Please report it to us as soon as possible, because we take security seriously and appreciate responsible disclosure.

## Reporting Security Vulnerabilities

Please report security vulnerability in this repository to us privately, so we can investigate, resolve and remediate as quickly as possible before public disclosure.

> **Do not** create public GitHub issues for security vulnerabilities!

### Preferred contact methods (in order):

1. Create a private Security Advisory via GitHub (recommended):
   - Go to this repository's [Security](https://github.com/LemonlightMC/Zenith/security) tab, and click "Report a vulnerability".
   - Follow the prompts to provide details, affected versions, reproduction steps, etc!!
   - _See also [Privately reporting a security vulnerability](https://docs.github.com/en/code-security/security-advisories/guidance-on-reporting-and-writing-information-about-vulnerabilities/privately-reporting-a-security-vulnerability#privately-reporting-a-security-vulnerability)_
2. Email: lemonlightmc.com

### Information to include

When reporting, please include:

- A clear, concise summary of the issue.
- Steps to reproduce the issue (ideally a minimal example).
- The impact of the issue and a suggested fix if you have one.
- Affected versions/commit hashes and environment details (OS, java version, Minecraft versions, plugins, mods, etc).
- Any exploit or proof-of-concept code (if available).

Do not post proof-of-concept exploit code publicly until the issue is resolved or we’ve agreed to disclosure.
Be aware that we may ask for additional information or clarification to help us investigate and resolve the issue.

## Scope

### In-scope

- Code in this repository (all branches unless otherwise noted).
- Configuration files stored in the repository.
- Any GitHub Actions workflows defined in this repository.

### Out-of-scope

- Third-party dependencies (please report upstream to the dependency maintainer as well).
- Services and infrastructure not managed in this repository (e.g., production servers, hosted APIs) unless explicitly documented as part of this project.
- Physical attacks, social engineering of third parties, or attacks against other users.

If you are unsure whether something is in-scope, please report it privately and we will clarify.

## Safe testing guidelines

- Do not access, modify or exfiltrate personal data you are not explicitly authorized to access.
- Do not perform destructive testing on production systems (e.g., data deletion, denial-of-service) without prior written permission.
- Limit testing to data you control, and avoid creating a public nuisance.

## Disclosure policy and timeline

We follow a typical responsible disclosure process. Our goals are to acknowledge and triage reports quickly and to resolve verified issues in a timely manner.

- **Acknowledgement:** within 7 business days of receipt.
- **Triage & initial response:** within 14 business days with an estimated remediation plan or request for more information.
- **Fix & release:** timelines vary depending on severity and complexity. We will coordinate with the reporter about disclosure.
- **Disclosure:** Once the issue is resolved, we will publicly disclose the vulnerability after providing credit to the reporter (if desired).

If you need faster acknowledgement or have not heard from us within these windows, follow up using the designated contact method.

---

**Thank you for helping keep Zenith and its users safe!**
